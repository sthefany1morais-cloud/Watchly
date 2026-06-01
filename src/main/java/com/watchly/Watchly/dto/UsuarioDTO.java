package com.watchly.Watchly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class UsuarioDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String nomeUsuario;
        private String email;
        private String senha; // Recebe a senha plain text
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nomeUsuario;
        private String email;
        private String imagemPerfil;
        private LocalDateTime criadoEm;
    }
}