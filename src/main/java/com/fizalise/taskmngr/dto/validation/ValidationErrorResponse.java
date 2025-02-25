package com.fizalise.taskmngr.dto.validation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ValidationErrorResponse(
        @Schema(description = "Список ошибок валидации") List<Violation> violations) {
}
