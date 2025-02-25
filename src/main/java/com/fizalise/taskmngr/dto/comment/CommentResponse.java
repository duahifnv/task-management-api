package com.fizalise.taskmngr.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.util.UUID;

public record CommentResponse(@Schema(description = "UUID комментария") UUID commentId,
                              @Schema(description = "Заголовок комментария")
                              String label,
                              @Schema(description = "Содержание комментария")
                              String message,
                              @Schema(description = "UUID задачи, к которой оставляется комментарий")
                              UUID taskId,
                              @Schema(description = "UUID пользователя, оставившего комментарий")
                              UUID userId,
                              @Schema(description = "Время создания комментария",
                                      format = "yyyy-mm-dd hh:mm:ss")
                              Timestamp creationTime) {
}
