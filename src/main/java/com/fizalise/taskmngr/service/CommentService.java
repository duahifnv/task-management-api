package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.dto.task.TaskRequest;
import com.fizalise.taskmngr.entity.Comment;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Сервис комментариев")
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskService taskService;
    private final UserService userService;
    public List<Comment> findAllComments() {
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "creationTime"));
    }
    public Comment findComment(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
    @Transactional
    public Comment addComment(CommentRequest commentRequest, String authorEmail) {
        Comment comment = commentMapper.toComment(
                commentRequest,
                taskService.findTask(commentRequest.taskId()),
                userService.getUserByEmail(authorEmail)
        );
        commentRepository.save(comment);
        log.info("К задаче {} был оставлен новый комментарий от администратора {}: {}",
                commentRequest.taskId(), authorEmail, comment);
        return comment;
    }
    @Transactional
    public Comment updateComment(UUID id, CommentRequest commentRequest) {
        Comment updatedComment = commentMapper.toComment(
                findComment(id),
                commentRequest,
                taskService.findTask(commentRequest.taskId())
        );
        commentRepository.save(updatedComment);
        log.info("Изменен комментарий: {}", updatedComment);
        return updatedComment;
    }
    @Transactional
    public void removeComment(UUID id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        commentRepository.deleteById(id);
        log.info("Удален комментарий с id: {}", id);
    }
}
