package com.watchly.Watchly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FilmeDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String titulo;
        private String descricao;
        private LocalDate dataLancamento;
        private Integer duracaoMinutos;
        private String urlPoster;
        private Set<String> generos = new HashSet<>();
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
        private Integer duracaoMinutos;
        private String urlPoster;
        private BigDecimal mediaAvaliacao;
        private Set<String> generos;
        private LocalDateTime criadoEm;
    }

    // DTO para o usuário ver seu próprio filme (progresso)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioFilmeResponse {
        private Long id;
        private String titulo;
        private String descricao;
        private LocalDate dataLancamento;
        private Integer duracaoMinutos;
        private String urlPoster;
        private String status; // NAO_INICIADO, ASSISTIDO
        private Boolean favorito;
        private LocalDateTime adicionadoEm;
    }
}