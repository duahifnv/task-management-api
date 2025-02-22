package com.fizalise.taskmngr.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequest(@NotNull UUID taskId,
                             String label,
                             @NotBlank String message) {
}
