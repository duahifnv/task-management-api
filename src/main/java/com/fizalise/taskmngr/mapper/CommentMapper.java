package com.fizalise.taskmngr.mapper;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.entity.Comment;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class, Timestamp.class, LocalDateTime.class })
public interface CommentMapper {
    @Mapping(target = "commentId", expression = "java(UUID.randomUUID())")
    @Mapping(source = "commentRequest.label", target = "label", defaultValue = "Комментарий")
    @Mapping(target = "creationTime", expression = "java(Timestamp.valueOf(LocalDateTime.now()))")
    Comment toComment(CommentRequest commentRequest, Task task, User user);
    @Mapping(source = "commentRequest.label", target = "label", defaultValue = "Комментарий")
    @Mapping(source = "commentRequest.message", target = "message")
    Comment toComment(Comment comment, CommentRequest commentRequest, Task task);
    @Mapping(source = "task", target = "taskId", qualifiedByName = "getTaskId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "getUserId")
    CommentResponse toResponse(Comment comment);
    List<CommentResponse> toResponses(Page<Comment> comments);
    @Named("getTaskId")
    default UUID getTaskId(Task task) {
        return task.getTaskId();
    }
    @Named("getUserId")
    default UUID getUserId(User user) {
        return user.getUserId();
    }

}
