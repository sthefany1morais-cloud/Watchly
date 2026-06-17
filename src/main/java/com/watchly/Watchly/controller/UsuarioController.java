package com.watchly.Watchly.controller;

import com.watchly.Watchly.dto.UsuarioDTO;
import com.watchly.Watchly.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para gestão de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna lista de todos os usuários (Apenas ADMIN)")
    public ResponseEntity<List<UsuarioDTO.Response>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico")
    public ResponseEntity<UsuarioDTO.Response> findById(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um usuário pelo email")
    public ResponseEntity<UsuarioDTO.Response> findByEmail(
            @Parameter(description = "Email do usuário") @PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados do usuário")
    public ResponseEntity<UsuarioDTO.Response> update(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestBody UsuarioDTO.Request request) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @PutMapping("/{id}/imagem")
    @Operation(summary = "Atualizar imagem de perfil", description = "Atualiza a URL da imagem de perfil do usuário")
    public ResponseEntity<UsuarioDTO.Response> atualizarImagemPerfil(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestParam String urlImagem) {
        return ResponseEntity.ok(usuarioService.atualizarImagemPerfil(id, urlImagem));
    }

    @PutMapping("/{id}/alterar-senha")
    @Operation(summary = "Atualizar senha", description = "Altera a senha do usuário")
    public ResponseEntity<Void> alterarSenha(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestBody UsuarioDTO.SenhaRequest request) {
        usuarioService.atualizarSenha(id, request.getSenhaAntiga(), request.getNovaSenha());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário (Apenas ADMIN)")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}