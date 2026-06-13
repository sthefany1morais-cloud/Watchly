package com.watchly.Watchly.controller;

import com.watchly.Watchly.dto.SerieDTO;
import com.watchly.Watchly.service.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@Tag(name = "Séries", description = "Endpoints para gestão de séries")
public class SerieController {

    private final SerieService serieService;

    @PostMapping
    @Operation(summary = "Criar série", description = "Cria uma nova série (Apenas ADMIN)")
    public ResponseEntity<SerieDTO.Response> create(@RequestBody SerieDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serieService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas as séries", description = "Retorna lista de todas as séries (Usuários autenticados)")
    public ResponseEntity<List<SerieDTO.Response>> findAll() {
        return ResponseEntity.ok(serieService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar série por ID", description = "Retorna uma série específica")
    public ResponseEntity<SerieDTO.Response> findById(
            @Parameter(description = "ID da série") @PathVariable Long id) {
        return ResponseEntity.ok(serieService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar série", description = "Atualiza uma série existente (Apenas ADMIN)")
    public ResponseEntity<SerieDTO.Response> update(
            @Parameter(description = "ID da série") @PathVariable Long id,
            @RequestBody SerieDTO.Request request) {
        return ResponseEntity.ok(serieService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir série", description = "Exclui uma série (Apenas ADMIN)")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da série") @PathVariable Long id) {
        serieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS DO USUÁRIO ====================

    @PostMapping("/{serieId}/usuario/{usuarioId}")
    @Operation(summary = "Adicionar série à lista do usuário", description = "Adiciona uma série à lista de acompanhamento do usuário")
    public ResponseEntity<SerieDTO.UsuarioSerieResponse> adicionarSerie(
            @Parameter(description = "ID da série") @PathVariable Long serieId,
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(serieService.adicionarSerie(usuarioId, serieId));
    }

    @PostMapping("/{serieId}/favorito/usuario/{usuarioId}")
    @Operation(summary = "Favoritar/desfavoritar série", description = "Marca ou desmarca uma série como favorita")
    public ResponseEntity<Boolean> favoritar(
            @Parameter(description = "ID da série") @PathVariable Long serieId,
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(serieService.favoritar(usuarioId, serieId));
    }

    @PutMapping("/episodios/{episodioId}/usuario/{usuarioId}")
    @Operation(summary = "Assistir episódio", description = "Marca ou desmarca um episódio como assistido atualizando o progresso automaticamente")
    public ResponseEntity<Void> assistirEpisodio(
            @Parameter(description = "ID do episódio") @PathVariable Long episodioId,
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId,
            @RequestParam Boolean assistido) {
        serieService.asistirEpisodio(usuarioId, episodioId, assistido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}/lista")
    @Operation(summary = "Lista de séries do usuário", description = "Retorna todas as séries que o usuário adicionou à sua lista")
    public ResponseEntity<List<SerieDTO.UsuarioSerieResponse>> listarListaDoUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(serieService.listarListaDoUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/favoritos")
    @Operation(summary = "Lista de favoritos do usuário", description = "Retorna todas as séries favoritas do usuário")
    public ResponseEntity<List<SerieDTO.UsuarioSerieResponse>> listarFavoritosDoUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(serieService.listarFavoritosDoUsuario(usuarioId));
    }
}