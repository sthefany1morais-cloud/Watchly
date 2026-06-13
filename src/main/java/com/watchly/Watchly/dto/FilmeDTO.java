package com.watchly.Watchly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "DTO de Filme")
public class FilmeDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para criar/editar filme")
    public static class Request {
        @Schema(description = "Título do filme", example = "O Poderoso Chefão")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "Duração em minutos", example = "175")
        private Integer duracaoMinutos;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Gêneros")
        private Set<String> generos = new HashSet<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response de filme")
    public static class Response {
        @Schema(description = "ID", example = "1")
        private Long id;

        @Schema(description = "Título")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "Duração em minutos")
        private Integer duracaoMinutos;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Média de avaliação")
        private BigDecimal mediaAvaliacao;

        @Schema(description = "Gêneros")
        private Set<String> generos;

        @Schema(description = "Data de criação")
        private LocalDateTime criadoEm;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response de filme na lista do usuário")
    public static class UsuarioFilmeResponse {
        @Schema(description = "ID")
        private Long id;

        @Schema(description = "Título")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "Duração em minutos")
        private Integer duracaoMinutos;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Status (NAO_INICIADO, ASSISTIDO)")
        private String status;

        @Schema(description = "É favorito")
        private Boolean favorito;

        @Schema(description = "Data que foi adicionado")
        private LocalDateTime adicionadoEm;
    }
}