package com.fizalise.taskmngr.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequest(@Schema(description = "UUID задачи, к которой оставляется комментарий")
                             @NotNull UUID taskId,
                             @Schema(description = "Заголовок комментария")
                             String label,
                             @Schema(description = "Содержание комментария")
                             @NotBlank String message) {
}
