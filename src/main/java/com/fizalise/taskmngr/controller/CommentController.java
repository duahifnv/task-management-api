package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.service.CommentService;
import jakarta.validation.Valid;
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments(Authentication authentication) {
        return commentMapper.toResponses(
                commentService.findAllComments(authentication)
        );
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse getComment(@PathVariable UUID id, Authentication authentication) {
        return commentMapper.toResponse(
                commentService.findComment(id, authentication)
        );
    }
    @GetMapping("/tasks/{taskId}")
    public List<CommentResponse> getTaskComments(@PathVariable UUID taskId,
                                                 Authentication authentication) {
        return commentMapper.toResponses(
                commentService.findAllCommentsByTask(taskId, authentication)
        );
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@Valid @RequestBody CommentRequest commentRequest,
                                      Authentication authentication) {
        return commentMapper.toResponse(
                commentService.addComment(commentRequest, authentication)
        );
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse updateComment(@PathVariable UUID id,
                                         @Valid @RequestBody CommentRequest commentRequest,
                                         Authentication authentication) {
        return commentMapper.toResponse(
                commentService.updateComment(id, commentRequest, authentication)
        );
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable UUID id, Authentication authentication) {
        commentService.removeComment(id, authentication);
    }
}
