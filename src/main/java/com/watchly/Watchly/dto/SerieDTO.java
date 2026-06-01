package com.watchly.Watchly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SerieDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String titulo;
        private String descricao;
        private LocalDate dataLancamento;
        private String urlPoster;
        private List<String> generos;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String titulo;
        private String descricao;
        private LocalDate dataLancamento;
        private String urlPoster;
        private BigDecimal mediaAvaliacao;
        private List<String> generos;
        private Integer totalTemporadas;
    }

    // DTO para o usuário ver sua própria série (progresso)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioSerieResponse {
        private Long id;
        private String titulo;
        private String descricao;
        private LocalDate dataLancamento;
        private String urlPoster;
        private String status; // NAO_INICIADO, INCOMPLETO, ASSISTIDO
        private Boolean favorito;
        private List<TemporadaProgresso> temporadas;
        private LocalDateTime adicionadoEm;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemporadaProgresso {
        private Integer numero;
        private String status; // NAO_INICIADO, INCOMPLETO, ASSISTIDO
        private Integer episodiosAssistidos;
        private Integer totalEpisodios;
    }
}