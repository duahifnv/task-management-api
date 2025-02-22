package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments/crud")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class CommentControllerCrud {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments() {
        return commentMapper.toResponses(
                commentService.findAllComments()
        );
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse getComment(@PathVariable UUID id) {
        return commentMapper.toResponse(
                commentService.findComment(id)
        );
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@Valid @RequestBody CommentRequest commentRequest, Principal principal) {
        return commentMapper.toResponse(
                commentService.addComment(commentRequest, principal.getName())
        );
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse updateComment(@PathVariable UUID id,
                                         @Valid @RequestBody CommentRequest commentRequest) {
        return commentMapper.toResponse(
                commentService.updateComment(id, commentRequest)
        );
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable UUID id) {
        commentService.removeComment(id);
    }
}
