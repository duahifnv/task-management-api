package com.fizalise.taskmngr.dto.task;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TaskRequest(@Schema(description = "Заголовок задачи") @NotBlank String label,
                          @Schema(description = "Описание задачи") String description,
                          @Schema(description = "Статус задачи", enumAsRef = true) Status status,
                          @Schema(description = "Приоритет задачи", enumAsRef = true)
                          @NotNull Priority priority,
                          @Schema(description = "Список почт исполнителей задачи")
                          @NotEmpty List<String> executorEmailList) {
}
