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
    private static final Sort COMMENT_SORTING_STRATEGY = Sort.by(Sort.Direction.DESC, "creationTime");
    public List<Comment> findAllComments() {
        return commentRepository.findAll(COMMENT_SORTING_STRATEGY);
    }
    public List<Comment> findAllComments(String authorEmail) {
        return commentRepository.findAllByUser(
                userService.getUserByEmail(authorEmail),
                COMMENT_SORTING_STRATEGY
        );
    }
    public List<Comment> findAllCommentsByTask(UUID taskId, String authorEmail) {
        Task task = taskService.findTask(taskId);
        if (!task.getExecutorList().contains(
                userService.getUserByEmail(authorEmail)
        )) {
            log.info("Попытка доступа к комментариям чужой задачи от пользователя {}", authorEmail);
            throw new ResourceNotFoundException();
        }
        return commentRepository.findAllByTask(task, COMMENT_SORTING_STRATEGY);
    }
    public Comment findComment(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
    public Comment findComment(UUID id, String authorEmail) {
        Comment comment = findComment(id);
        if (!comment.getUser().getEmail()
                .equals(authorEmail)) {
            log.info("Попытка доступа к чужому комментарию от пользователя {}", authorEmail);
            throw new ResourceNotFoundException();
        }
        return comment;
    }
    @Transactional
    public Comment addCommentFromAdmin(CommentRequest commentRequest, String authorEmail) {
        Comment comment = addComment(commentRequest,
                taskService.findTask(commentRequest.taskId()),
                authorEmail);
        log.info("К задаче {} был оставлен новый комментарий от администратора {}: {}",
                commentRequest.taskId(), authorEmail, comment);
        return comment;
    }
    @Transactional
    public Comment addCommentFromUser(CommentRequest commentRequest, String authorEmail) {
        Comment comment = addComment(commentRequest,
                taskService.findTask(commentRequest.taskId(), authorEmail),
                authorEmail);
        log.info("К задаче {} был оставлен новый комментарий от пользователя {}: {}",
                commentRequest.taskId(), authorEmail, comment);
        return comment;
    }
    @Transactional
    public Comment addComment(CommentRequest commentRequest, Task task, String authorEmail) {
        Comment comment = commentMapper.toComment(
                commentRequest, task, userService.getUserByEmail(authorEmail)
        );
        commentRepository.save(comment);
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
