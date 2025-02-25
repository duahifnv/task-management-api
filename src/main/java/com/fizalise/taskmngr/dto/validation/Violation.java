package com.fizalise.taskmngr.dto.validation;

import io.swagger.v3.oas.annotations.media.Schema;

public record Violation(@Schema(description = "Поле, непрошедшее валидацию") String fieldName,
                        @Schema(description = "Описание ошибки") String message) {
}
