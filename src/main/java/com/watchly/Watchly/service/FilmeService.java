package com.watchly.Watchly.service;

import com.watchly.Watchly.dto.FilmeDTO;
import com.watchly.Watchly.model.*;
import com.watchly.Watchly.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final GeneroRepository generoRepository;
    private final FilmeFavoritoRepository filmeFavoritoRepository;
    private final UsuarioFilmeRepository usuarioFilmeRepository;
    private final UsuarioRepository usuarioRepository;

    // ===================== CRUD ADMIN =====================

    @Transactional
    public FilmeDTO.Response create(FilmeDTO.Request request) {
        FilmeEntity entity = new FilmeEntity();
        mapRequestToEntity(request, entity);
        entity.setCriadoEm(LocalDateTime.now());
        return mapEntityToResponse(filmeRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<FilmeDTO.Response> findAll() {
        return filmeRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FilmeDTO.Response findById(Long id) {
        FilmeEntity entity = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
        return mapEntityToResponse(entity);
    }

    @Transactional
    public FilmeDTO.Response update(Long id, FilmeDTO.Request request) {
        FilmeEntity entity = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
        mapRequestToEntity(request, entity);
        return mapEntityToResponse(filmeRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado");
        }

        filmeRepository.deleteById(id);
    }

    // ===================== USUÁRIO =====================

    /**
     * Adiciona ou atualiza o status de um filme na lista do usuário.
     * Se o filme ainda não estiver na lista, adiciona como NÃO_INICIADO.
     */
    @Transactional
    public FilmeDTO.UsuarioFilmeResponse adicionarOuAtualizarFilme(
            Long usuarioId,
            Long filmeId,
            String novoStatus) {

        UsuarioEntity usuario = getUsuarioOrThrow(usuarioId);

        FilmeEntity filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        // Validação de status
        if (!"NAO_INICIADO".equals(novoStatus)
                && !"ASSISTIDO".equals(novoStatus)) {

            throw new IllegalArgumentException(
                    "Status inválido. Utilize NAO_INICIADO ou ASSISTIDO."
            );
        }

        UsuarioFilmeEntity usuarioFilme =
                usuarioFilmeRepository.findByUsuarioIdAndFilmeId(usuarioId, filmeId)
                        .orElseGet(() -> {
                            UsuarioFilmeEntity novo = new UsuarioFilmeEntity();
                            novo.setUsuario(usuario);
                            novo.setFilme(filme);
                            novo.setAdicionadoEm(LocalDateTime.now());
                            return novo;
                        });

        usuarioFilme.setStatus(novoStatus);

        usuarioFilmeRepository.save(usuarioFilme);

        return mapToUsuarioFilmeResponse(usuarioFilme);
    }

    /**
     * Favorita ou desfavorita um filme para o usuário.
     */
    @Transactional
    public Boolean favoritar(Long usuarioId, Long filmeId) {
        UsuarioEntity usuario = getUsuarioOrThrow(usuarioId);
        FilmeEntity filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        FilmeFavoritoEntity favorito = filmeFavoritoRepository.findByUsuarioIdAndFilmeId(usuarioId, filmeId)
                .orElse(null);

        if (favorito != null) {
            // Se já é favorito, remove
            filmeFavoritoRepository.delete(favorito);
            return false;
        } else {
            // Se não é favorito, adiciona
            FilmeFavoritoEntity novo = new FilmeFavoritoEntity();
            novo.setUsuario(usuario);
            novo.setFilme(filme);
            filmeFavoritoRepository.save(novo);
            return true;
        }
    }

    @Transactional(readOnly = true)
    public List<FilmeDTO.UsuarioFilmeResponse> listarListaDoUsuario(Long usuarioId) {
        return usuarioFilmeRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToUsuarioFilmeResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FilmeDTO.UsuarioFilmeResponse> listarFavoritosDoUsuario(Long usuarioId) {
        return filmeFavoritoRepository.findByUsuarioId(usuarioId).stream()
                .map(fav -> mapToUsuarioFilmeResponseByEntity(fav.getFilme(), true, fav.getUsuario().getId()))
                .collect(Collectors.toList());
    }

    // ===================== HELPERS =====================

    private void mapRequestToEntity(FilmeDTO.Request request, FilmeEntity entity) {
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setDataLancamento(request.getDataLancamento());
        entity.setDuracaoMinutos(request.getDuracaoMinutos());
        entity.setUrlPoster(request.getUrlPoster());

        Set<GeneroEntity> generos = request.getGeneros().stream()
                .map(nome -> generoRepository.findByNome(nome)
                        .orElseGet(() -> {
                            GeneroEntity novo = new GeneroEntity();
                            novo.setNome(nome);
                            return generoRepository.save(novo);
                        }))
                .collect(Collectors.toSet());
        entity.setGeneros(generos);
    }

    private FilmeDTO.Response mapEntityToResponse(FilmeEntity entity) {
        FilmeDTO.Response response = new FilmeDTO.Response();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setDataLancamento(entity.getDataLancamento());
        response.setDuracaoMinutos(entity.getDuracaoMinutos());
        response.setUrlPoster(entity.getUrlPoster());
        response.setMediaAvaliacao(entity.getMediaAvaliacao());
        response.setCriadoEm(entity.getCriadoEm());

        response.setGeneros(entity.getGeneros().stream()
                .map(GeneroEntity::getNome)
                .collect(Collectors.toSet()));

        return response;
    }

    private FilmeDTO.UsuarioFilmeResponse mapToUsuarioFilmeResponse(UsuarioFilmeEntity uf) {
        FilmeEntity f = uf.getFilme();
        Boolean isFavorito = filmeFavoritoRepository.findByUsuarioIdAndFilmeId(uf.getUsuario().getId(), f.getId()).isPresent();
        return mapToUsuarioFilmeResponseByEntity(f, isFavorito, uf.getUsuario().getId());
    }

    private FilmeDTO.UsuarioFilmeResponse mapToUsuarioFilmeResponseByEntity(FilmeEntity f, Boolean favorito, Long usuarioId) {
        FilmeDTO.UsuarioFilmeResponse response = new FilmeDTO.UsuarioFilmeResponse();
        response.setId(f.getId());
        response.setTitulo(f.getTitulo());
        response.setDescricao(f.getDescricao());
        response.setDataLancamento(f.getDataLancamento());
        response.setDuracaoMinutos(f.getDuracaoMinutos());
        response.setUrlPoster(f.getUrlPoster());

        response.setFavorito(favorito);

        // Pega o status da tabela usuario_filme
        usuarioFilmeRepository.findByUsuarioIdAndFilmeId(usuarioId, f.getId())
                .ifPresent(uf -> {
                    response.setStatus(uf.getStatus());
                    response.setAdicionadoEm(uf.getAdicionadoEm());
                });

        return response;
    }

    private UsuarioEntity getUsuarioOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}