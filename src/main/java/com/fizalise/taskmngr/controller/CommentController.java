package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<CommentResponse> getAllComments(Principal principal) {
        return commentMapper.toResponses(
                commentService.findAllComments(principal.getName())
        );
    }
    @GetMapping("/{id}")
    public CommentResponse getComment(@PathVariable UUID id, Principal principal) {
        return commentMapper.toResponse(
                commentService.findComment(id, principal.getName())
        );
    }
    @GetMapping("/tasks/{taskId}")
    public List<CommentResponse> getTaskComments(@PathVariable UUID taskId, Principal principal) {
        return commentMapper.toResponses(
                commentService.findAllCommentsByTask(taskId, principal.getName())
        );
    }
    @PostMapping
    public CommentResponse addComment(@Valid @RequestBody CommentRequest commentRequest,
                                      Principal principal) {
        return commentMapper.toResponse(
                commentService.addCommentFromUser(commentRequest, principal.getName())
        );
    }
}
