package com.watchly.Watchly.controller;

import com.watchly.Watchly.dto.FilmeDTO;
import com.watchly.Watchly.service.FilmeService;
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
@RequestMapping("/api/filmes")
@RequiredArgsConstructor
@Tag(name = "Filmes", description = "Endpoints para gestão de filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @PostMapping
    @Operation(summary = "Criar filme", description = "Cria um novo filme (Apenas ADMIN)")
    public ResponseEntity<FilmeDTO.Response> create(@RequestBody FilmeDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos os filmes", description = "Retorna lista de todos os filmes (Usuários autenticados)")
    public ResponseEntity<List<FilmeDTO.Response>> findAll() {
        return ResponseEntity.ok(filmeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID", description = "Retorna um filme específico")
    public ResponseEntity<FilmeDTO.Response> findById(
            @Parameter(description = "ID do filme") @PathVariable Long id) {
        return ResponseEntity.ok(filmeService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Atualiza um filme existente (Apenas ADMIN)")
    public ResponseEntity<FilmeDTO.Response> update(
            @Parameter(description = "ID do filme") @PathVariable Long id,
            @RequestBody FilmeDTO.Request request) {
        return ResponseEntity.ok(filmeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir filme", description = "Exclui um filme (Apenas ADMIN)")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do filme") @PathVariable Long id) {
        filmeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{filmeId}/usuario/{usuarioId}")
    @Operation(summary = "Adicionar filme à lista do usuário", description = "Adiciona ou atualiza o status de um filme na lista do usuário")
    public ResponseEntity<FilmeDTO.UsuarioFilmeResponse> adicionarFilme(
            @Parameter(description = "ID do filme") @PathVariable Long filmeId,
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId,
            @RequestParam String status) {
        return ResponseEntity.ok(filmeService.adicionarOuAtualizarFilme(usuarioId, filmeId, status));
    }

    @PostMapping("/{filmeId}/favorito/usuario/{usuarioId}")
    @Operation(summary = "Favoritar/desfavoritar filme", description = "Marca ou desmarca um filme como favorito")
    public ResponseEntity<Boolean> favoritar(
            @Parameter(description = "ID do filme") @PathVariable Long filmeId,
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(filmeService.favoritar(usuarioId, filmeId));
    }

    @GetMapping("/usuario/{usuarioId}/lista")
    @Operation(summary = "Lista de filmes do usuário", description = "Retorna todos os filmes que o usuário adicionou à sua lista")
    public ResponseEntity<List<FilmeDTO.UsuarioFilmeResponse>> listarListaDoUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(filmeService.listarListaDoUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/favoritos")
    @Operation(summary = "Lista de favoritos do usuário", description = "Retorna todos os filmes favoritos do usuário")
    public ResponseEntity<List<FilmeDTO.UsuarioFilmeResponse>> listarFavoritosDoUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(filmeService.listarFavoritosDoUsuario(usuarioId));
    }
}