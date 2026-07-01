package com.watchly.Watchly.service;

import com.watchly.Watchly.dto.TokenDTO;
import com.watchly.Watchly.dto.UsuarioDTO;
import com.watchly.Watchly.model.UsuarioEntity;
import com.watchly.Watchly.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.watchly.Watchly.config.JwtService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public TokenDTO create(UsuarioDTO.Request request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (usuarioRepository.findByNomeUsuario(request.getNomeUsuario()).isPresent()) {
            throw new IllegalArgumentException("Nome de usuário já está em uso");
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNomeUsuario(request.getNomeUsuario());
        entity.setEmail(request.getEmail());
        entity.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        entity.setCriadoEm(LocalDateTime.now());
        entity.setImagemPerfil("https://cdn.watchly.com/default-avatar.png");
        entity.setRole("USER");

        UsuarioEntity saved = usuarioRepository.save(entity);

        String token = jwtService.generateToken(saved);

        return new TokenDTO(token);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response findById(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response findByEmail(String email) {
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response findByNomeUsuario(String nomeUsuario) {
        UsuarioEntity entity = usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapToResponse(entity);
    }


    @Transactional
    public UsuarioDTO.Response update(Long id, UsuarioDTO.Request request) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.findByEmail(request.getEmail())
                .ifPresent(userExistente -> {
                    if (!userExistente.getId().equals(id)) {
                        throw new IllegalArgumentException("Email já está em uso");
                    }
                });

        usuarioRepository.findByNomeUsuario(request.getNomeUsuario())
                .ifPresent(userExistente -> {
                    if (!userExistente.getId().equals(id)) {
                        throw new IllegalArgumentException("Nome de usuário já está em uso");
                    }
                });

        entity.setNomeUsuario(request.getNomeUsuario());
        entity.setEmail(request.getEmail());

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            entity.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        }

        UsuarioEntity updated = usuarioRepository.save(entity);
        return mapToResponse(updated);
    }

    @Transactional
    public UsuarioDTO.Response atualizarImagemPerfil(Long id, String urlImagem) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        entity.setImagemPerfil(urlImagem);
        UsuarioEntity updated = usuarioRepository.save(entity);
        return mapToResponse(updated);
    }

    @Transactional
    public void atualizarSenha(Long id, String senhaAntiga, String novaSenha) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAntiga, entity.getSenhaHash())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        entity.setSenhaHash(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO.Response mapToResponse(UsuarioEntity entity) {
        UsuarioDTO.Response response = new UsuarioDTO.Response();
        response.setId(entity.getId());
        response.setNomeUsuario(entity.getNomeUsuario());
        response.setEmail(entity.getEmail());
        response.setImagemPerfil(entity.getImagemPerfil());

        if (entity.getCriadoEm() != null) {
            response.setCriadoEm(entity.getCriadoEm().toString());
        }

        return response;
    }
}