package com.fizalise.taskmngr.dto.comment;

import java.sql.Timestamp;
import java.util.UUID;

public record CommentResponse(UUID commentId,
                              String label,
                              String message,
                              UUID taskId,
                              UUID userId,
                              Timestamp creationTime) {
}
