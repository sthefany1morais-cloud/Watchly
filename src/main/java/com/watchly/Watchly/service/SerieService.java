package com.watchly.Watchly.service;

import com.watchly.Watchly.dto.SerieDTO;
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
public class SerieService {

    private final SerieRepository serieRepository;
    private final GeneroRepository generoRepository;
    private final SerieFavoritaRepository serieFavoritoRepository;
    private final UsuarioSerieRepository usuarioSerieRepository;
    private final UsuarioTemporadaRepository usuarioTemporadaRepository;
    private final UsuarioEpisodioRepository usuarioEpisodioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TemporadaRepository temporadaRepository;
    private final EpisodioRepository episodioRepository;

    @Transactional
    public SerieDTO.Response create(SerieDTO.Request request) {
        SerieEntity entity = new SerieEntity();
        mapRequestToEntity(request, entity);
        entity.setCriadoEm(LocalDateTime.now());
        return mapEntityToResponse(serieRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<SerieDTO.Response> findAll() {
        return serieRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SerieDTO.Response findById(Long id) {
        SerieEntity entity = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));
        return mapEntityToResponse(entity);
    }

    @Transactional
    public SerieDTO.Response update(Long id, SerieDTO.Request request) {
        SerieEntity entity = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));
        mapRequestToEntity(request, entity);
        return mapEntityToResponse(serieRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {

        SerieEntity serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

        serieRepository.delete(serie);
    }

    @Transactional
    public SerieDTO.UsuarioSerieResponse adicionarSerie(Long usuarioId, Long serieId) {
        UsuarioEntity usuario = getUsuarioOrThrow(usuarioId);
        SerieEntity serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

        UsuarioSerieEntity usuarioSerie = usuarioSerieRepository.findByUsuarioIdAndSerieId(usuarioId, serieId)
                .orElseGet(() -> {
                    UsuarioSerieEntity novo = new UsuarioSerieEntity();
                    novo.setUsuario(usuario);
                    novo.setSerie(serie);
                    novo.setStatus("NAO_INICIADO");
                    novo.setAdicionadoEm(LocalDateTime.now());
                    return novo;
                });

        usuarioSerieRepository.save(usuarioSerie);
        inicializarProgressoTemporadas(usuario, serie);

        return mapToUsuarioSerieResponse(usuarioSerie, usuarioId);
    }

    @Transactional
    public Boolean favoritar(Long usuarioId, Long serieId) {
        UsuarioEntity usuario = getUsuarioOrThrow(usuarioId);
        SerieEntity serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

        SerieFavoritaEntity favorito = serieFavoritoRepository.findByUsuarioIdAndSerieId(usuarioId, serieId)
                .orElse(null);

        if (favorito != null) {
            serieFavoritoRepository.delete(favorito);
            return false;
        } else {
            SerieFavoritaEntity novo = new SerieFavoritaEntity();
            novo.setUsuario(usuario);
            novo.setSerie(serie);
            serieFavoritoRepository.save(novo);
            return true;
        }
    }

    @Transactional
    public void asistirEpisodio(Long usuarioId, Long episodioId, Boolean assistido) {
        UsuarioEntity usuario = getUsuarioOrThrow(usuarioId);
        EpisodioEntity episodio = episodioRepository.findById(episodioId)
                .orElseThrow(() -> new RuntimeException("Episódio não encontrado"));

        TemporadaEntity temporada = episodio.getTemporada();
        SerieEntity serie = temporada.getSerie();

        UsuarioEpisodioEntity usuarioEpisodio = usuarioEpisodioRepository
                .findByUsuarioIdAndEpisodioId(usuarioId, episodioId)
                .orElseGet(() -> {
                    UsuarioEpisodioEntity novo = new UsuarioEpisodioEntity();
                    novo.setUsuario(usuario);
                    novo.setEpisodio(episodio);
                    return novo;
                });

        usuarioEpisodio.setAssistido(assistido);
        if (assistido) {
            usuarioEpisodio.setAssistidoEm(LocalDateTime.now());
        } else {
            usuarioEpisodio.setAssistidoEm(null);
        }

        usuarioEpisodioRepository.save(usuarioEpisodio);

        atualizarProgressoTemporada(usuarioId, temporada);

        atualizarProgressoSerie(usuarioId, serie);
    }

    @Transactional(readOnly = true)
    public List<SerieDTO.UsuarioSerieResponse> listarListaDoUsuario(Long usuarioId) {
        return usuarioSerieRepository.findByUsuarioId(usuarioId).stream()
                .map(us -> mapToUsuarioSerieResponse(us, usuarioId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SerieDTO.UsuarioSerieResponse> listarFavoritosDoUsuario(Long usuarioId) {
        return serieFavoritoRepository.findByUsuarioId(usuarioId).stream()
                .map(fav -> mapToUsuarioSerieResponseByEntity(fav.getSerie(), true, usuarioId))
                .collect(Collectors.toList());
    }

    private void inicializarProgressoTemporadas(UsuarioEntity usuario, SerieEntity serie) {
        for (TemporadaEntity temporada : serie.getTemporadas()) {
            UsuarioTemporadaEntity ut = usuarioTemporadaRepository
                    .findByUsuarioIdAndTemporadaId(usuario.getId(), temporada.getId())
                    .orElseGet(() -> {
                        UsuarioTemporadaEntity novo = new UsuarioTemporadaEntity();
                        novo.setUsuario(usuario);
                        novo.setTemporada(temporada);
                        novo.setStatus("NAO_INICIADO");
                        novo.setAtualizadoEm(LocalDateTime.now());
                        return novo;
                    });
            usuarioTemporadaRepository.save(ut);
        }
    }

    private void atualizarProgressoTemporada(Long usuarioId, TemporadaEntity temporada) {
        List<EpisodioEntity> episodios = temporada.getEpisodios().stream().collect(Collectors.toList());

        if (episodios.isEmpty()) return;

        long assistidos = episodios.stream()
                .filter(ep -> usuarioEpisodioRepository
                        .findByUsuarioIdAndEpisodioId(usuarioId, ep.getId())
                        .map(UsuarioEpisodioEntity::getAssistido)
                        .orElse(false))
                .count();

        String statusTemporada;
        if (assistidos == 0) {
            statusTemporada = "NAO_INICIADO";
        } else if (assistidos == episodios.size()) {
            statusTemporada = "ASSISTIDO";
        } else {
            statusTemporada = "INCOMPLETO";
        }

        UsuarioTemporadaEntity ut = usuarioTemporadaRepository
                .findByUsuarioIdAndTemporadaId(usuarioId, temporada.getId())
                .orElseThrow(() -> new RuntimeException("Progresso da temporada não encontrado"));

        if (!ut.getStatus().equals(statusTemporada)) {
            ut.setStatus(statusTemporada);
            ut.setAtualizadoEm(LocalDateTime.now());
            usuarioTemporadaRepository.save(ut);
        }
    }

    private void atualizarProgressoSerie(Long usuarioId, SerieEntity serie) {
        List<TemporadaEntity> temporadas = serie.getTemporadas().stream().collect(Collectors.toList());

        if (temporadas.isEmpty()) return;

        long temporadasCompletas = temporadas.stream()
                .filter(t -> {
                    String status = usuarioTemporadaRepository
                            .findByUsuarioIdAndTemporadaId(usuarioId, t.getId())
                            .map(UsuarioTemporadaEntity::getStatus)
                            .orElse("NAO_INICIADO");
                    return "ASSISTIDO".equals(status);
                })
                .count();

        String statusSerie;
        if (temporadasCompletas == 0) {
            statusSerie = "NAO_INICIADO";
        } else if (temporadasCompletas == temporadas.size()) {
            statusSerie = "ASSISTIDO";
        } else {
            statusSerie = "INCOMPLETO";
        }

        UsuarioSerieEntity us = usuarioSerieRepository.findByUsuarioIdAndSerieId(usuarioId, serie.getId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada na lista do usuário"));

        if (!us.getStatus().equals(statusSerie)) {
            us.setStatus(statusSerie);
            usuarioSerieRepository.save(us);
        }
    }

    private void mapRequestToEntity(SerieDTO.Request request, SerieEntity entity) {
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setDataLancamento(request.getDataLancamento());
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

    private SerieDTO.Response mapEntityToResponse(SerieEntity entity) {
        SerieDTO.Response response = new SerieDTO.Response();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setDataLancamento(entity.getDataLancamento());
        response.setUrlPoster(entity.getUrlPoster());
        response.setMediaAvaliacao(entity.getMediaAvaliacao());

        response.setTotalTemporadas(entity.getTemporadas() != null ? entity.getTemporadas().size() : 0);

        response.setGeneros(entity.getGeneros().stream()
                .map(GeneroEntity::getNome)
                .collect(Collectors.toList()));

        return response;
    }

    private SerieDTO.UsuarioSerieResponse mapToUsuarioSerieResponse(UsuarioSerieEntity us, Long usuarioId) {
        SerieEntity s = us.getSerie();
        Boolean isFavorito = serieFavoritoRepository.findByUsuarioIdAndSerieId(usuarioId, s.getId()).isPresent();
        return mapToUsuarioSerieResponseByEntity(s, isFavorito, usuarioId);
    }

    private SerieDTO.UsuarioSerieResponse mapToUsuarioSerieResponseByEntity(SerieEntity s, Boolean favorito, Long usuarioId) {
        SerieDTO.UsuarioSerieResponse response = new SerieDTO.UsuarioSerieResponse();
        response.setId(s.getId());
        response.setTitulo(s.getTitulo());
        response.setDescricao(s.getDescricao());
        response.setDataLancamento(s.getDataLancamento());
        response.setUrlPoster(s.getUrlPoster());
        response.setFavorito(favorito);

        usuarioSerieRepository.findByUsuarioIdAndSerieId(usuarioId, s.getId())
                .ifPresent(us -> {
                    response.setStatus(us.getStatus());
                    response.setAdicionadoEm(us.getAdicionadoEm());
                });

        List<SerieDTO.TemporadaProgresso> progressos = s.getTemporadas().stream()
                .map(t -> {
                    SerieDTO.TemporadaProgresso tp = new SerieDTO.TemporadaProgresso();
                    tp.setNumero(t.getNumero());

                    int total = t.getEpisodios().size();
                    tp.setTotalEpisodios(total);

                    long assistidos = t.getEpisodios().stream()
                            .filter(ep -> usuarioEpisodioRepository
                                    .findByUsuarioIdAndEpisodioId(usuarioId, ep.getId())
                                    .map(UsuarioEpisodioEntity::getAssistido)
                                    .orElse(false))
                            .count();

                    tp.setEpisodiosAssistidos((int) assistidos);

                    if (assistidos == 0) {
                        tp.setStatus("NAO_INICIADO");
                    } else if (assistidos == total) {
                        tp.setStatus("ASSISTIDO");
                    } else {
                        tp.setStatus("INCOMPLETO");
                    }

                    return tp;
                })
                .collect(Collectors.toList());

        response.setTemporadas(progressos);

        return response;
    }

    private UsuarioEntity getUsuarioOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}