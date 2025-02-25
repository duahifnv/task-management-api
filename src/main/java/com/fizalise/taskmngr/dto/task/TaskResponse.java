package com.fizalise.taskmngr.dto.task;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Date;
import java.util.UUID;

public record TaskResponse(@Schema(description = "UUID созданной задачи") UUID taskId,
                           @Schema(description = "Заголовок задачи") String label,
                           @Schema(description = "Описание задачи") String description,
                           @Schema(description = "Статус задачи", enumAsRef = true) Status status,
                           @Schema(description = "Приоритет задачи", enumAsRef = true) Priority priority,
                           @Schema(description = "UUID создателя задачи") UUID authorId,
                           @Schema(description = "Дата создания задачи", format = "yyyy-mm-dd")
                           Date creationDate) {
}
