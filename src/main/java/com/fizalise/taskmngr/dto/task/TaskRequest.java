package com.fizalise.taskmngr.dto.task;

import com.fizalise.taskmngr.entity.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record TaskRequest(@NotBlank String label,
                          String description,
                          @NotNull Priority priority,
                          List<String> executorEmailList) {
}
