package com.watchly.Watchly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO de Episódio")
public class EpisodioDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request para marcar episódio como assistido")
    public static class AssistirRequest {
        @Schema(description = "Assistido", example = "true")
        private Boolean assistido;
    }
}