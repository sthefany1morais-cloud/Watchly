package com.watchly.Watchly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO para requisição de usuário")
public class UsuarioDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para criar/editar usuário")
    public static class Request {
        @Schema(description = "Nome de usuário", example = "johndoe")
        private String nomeUsuario;

        @Schema(description = "Email do usuário", example = "john@example.com")
        private String email;

        @Schema(description = "Senha", example = "minhaSenha123")
        private String senha;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response de usuário")
    public static class Response {
        @Schema(description = "ID do usuário", example = "1")
        private Long id;

        @Schema(description = "Nome de usuário", example = "johndoe")
        private String nomeUsuario;

        @Schema(description = "Email do usuário", example = "john@example.com")
        private String email;

        @Schema(description = "URL da imagem de perfil")
        private String imagemPerfil;

        @Schema(description = "Data de criação")
        private String criadoEm;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para login")
    public static class LoginRequest {
        @Schema(description = "Email", example = "john@example.com")
        private String email;

        @Schema(description = "Senha", example = "minhaSenha123")
        private String senha;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para alterar senha")
    public static class SenhaRequest {
        @Schema(description = "Senha atual", example = "senhaAntiga123")
        private String senhaAntiga;

        @Schema(description = "Nova senha", example = "novaSenha123")
        private String novaSenha;
    }
}