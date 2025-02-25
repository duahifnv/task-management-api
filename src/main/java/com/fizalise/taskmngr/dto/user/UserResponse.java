package com.fizalise.taskmngr.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserResponse(@Schema(description = "UUID пользователя") UUID userId,
                           @Schema(description = "Почта") String email,
                           @Schema(description = "Имя пользователя", example = "Юзер") String name,
                           @Schema(description = "Фамилия пользователя", example = "Юзернеймов") String surname) {
}
