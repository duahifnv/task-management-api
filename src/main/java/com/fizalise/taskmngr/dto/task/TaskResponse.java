package com.fizalise.taskmngr.dto.task;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;

import java.sql.Date;
import java.util.UUID;

public record TaskResponse(UUID taskId, String label, String description, Status status,
                           Priority priority, UUID authorId, Date creationDate) {
}
