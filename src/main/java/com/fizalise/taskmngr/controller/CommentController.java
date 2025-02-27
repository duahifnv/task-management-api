package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @Operation(summary = "Получить список всех комментариев")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            Authentication authentication) {
        return commentMapper.toResponses(
                commentService.findAllComments(page, authentication)
        );
    }
    @Operation(summary = "Получить список комментариев к задаче")
    @GetMapping("/tasks/{taskId}")
    public List<CommentResponse> getTaskComments(@PathVariable UUID taskId,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                 Authentication authentication) {
        return commentMapper.toResponses(
                commentService.findAllCommentsByTask(taskId, page, authentication)
        );
    }
    @Operation(summary = "Получить комментарий")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse getComment(@PathVariable UUID id, Authentication authentication) {
        return commentMapper.toResponse(
                commentService.findComment(id, authentication)
        );
    }
    @Operation(summary = "Добавить новый комментарий к задаче")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@Valid @RequestBody CommentRequest commentRequest,
                                      Authentication authentication) {
        return commentMapper.toResponse(
                commentService.addComment(commentRequest, authentication)
        );
    }
    @Operation(summary = "Обновить существующий комментарий к задаче")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse updateComment(@PathVariable UUID id,
                                         @Valid @RequestBody CommentRequest commentRequest,
                                         Authentication authentication) {
        return commentMapper.toResponse(
                commentService.updateComment(id, commentRequest, authentication)
        );
    }
    @Operation(summary = "Удалить комментарий к задаче")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable UUID id, Authentication authentication) {
        commentService.removeComment(id, authentication);
    }
}
