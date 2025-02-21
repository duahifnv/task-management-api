package com.fizalise.taskmngr.dto.task;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TaskRequest(@NotBlank String label,
                          String description,
                          Status status,
                          @NotNull Priority priority,
                          List<String> executorEmailList) {
}
