package com.watchly.Watchly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "DTO de Série")
public class SerieDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para criar/editar série")
    public static class Request {
        @Schema(description = "Título", example = "Breaking Bad")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Gêneros")
        private List<String> generos;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response de série")
    public static class Response {
        @Schema(description = "ID", example = "1")
        private Long id;

        @Schema(description = "Título")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Média de avaliação")
        private BigDecimal mediaAvaliacao;

        @Schema(description = "Gêneros")
        private List<String> generos;

        @Schema(description = "Total de temporadas")
        private Integer totalTemporadas;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response de série na lista do usuário")
    public static class UsuarioSerieResponse {
        @Schema(description = "ID")
        private Long id;

        @Schema(description = "Título")
        private String titulo;

        @Schema(description = "Descrição")
        private String descricao;

        @Schema(description = "Data de lançamento")
        private LocalDate dataLancamento;

        @Schema(description = "URL do poster")
        private String urlPoster;

        @Schema(description = "Status")
        private String status;

        @Schema(description = "É favorito")
        private Boolean favorito;

        @Schema(description = "Progresso por temporada")
        private List<TemporadaProgresso> temporadas;

        @Schema(description = "Data que foi adicionado")
        private LocalDateTime adicionadoEm;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Progresso de uma temporada")
    public static class TemporadaProgresso {
        @Schema(description = "Número da temporada", example = "1")
        private Integer numero;

        @Schema(description = "Status da temporada")
        private String status;

        @Schema(description = "Episódios assistidos")
        private Integer episodiosAssistidos;

        @Schema(description = "Total de episódios")
        private Integer totalEpisodios;
    }
}