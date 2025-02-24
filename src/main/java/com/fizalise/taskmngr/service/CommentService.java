package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.dto.comment.CommentRequest;
import com.fizalise.taskmngr.entity.Comment;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.repository.CommentRepository;
import com.fizalise.taskmngr.repository.sort.CommentSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
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
    private final AuthService authService;
    public List<Comment> findAllComments(Authentication authentication) {
        if (!authService.hasAdminRole(authentication)) {
            return commentRepository.findAllByUser(
                    userService.findByEmail(authentication.getName()),
                    CommentSort.CREATED_DESC.getSort()
            );
        }
        return commentRepository.findAll(CommentSort.CREATED_DESC.getSort());
    }
    public List<Comment> findAllCommentsByTask(UUID taskId, Authentication authentication) {
        Task task = taskService.findTask(taskId, authentication);
        return commentRepository.findAllByTask(task, CommentSort.CREATED_DESC.getSort());
    }
    public Comment findComment(UUID id, Authentication authentication) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (!authService.hasAdminRole(authentication) &&
                !comment.getUser().getEmail().equals(authentication.getName())
        ) {
            log.info("Попытка доступа к чужому комментарию от пользователя {}", authentication.getName());
            throw new ResourceNotFoundException();
        }
        return comment;
    }
    @Transactional
    public Comment addComment(CommentRequest commentRequest, Authentication authentication) {
        Comment comment = commentMapper.toComment(
                commentRequest,
                taskService.findTask(commentRequest.taskId(), authentication),
                userService.findByEmail(authentication.getName())
        );
        commentRepository.save(comment);
        log.info("К задаче {} был оставлен новый комментарий от {}: {}",
                commentRequest.taskId(), authentication.getName(), comment);
        return comment;
    }
    @Transactional
    public Comment updateComment(UUID id, CommentRequest commentRequest, Authentication authentication) {
        Comment updatedComment = commentMapper.toComment(
                findComment(id, authentication),
                commentRequest,
                taskService.findTask(commentRequest.taskId(), authentication)
        );
        commentRepository.save(updatedComment);
        log.info("Изменен комментарий: {}", updatedComment);
        return updatedComment;
    }
    @Transactional
    public void removeComment(UUID id, Authentication authentication) {
        if (existsByCommentId(id, authentication)) {
            throw new ResourceNotFoundException();
        }
        commentRepository.deleteById(id);
        log.info("Удален комментарий с id: {}", id);
    }
    public boolean existsByCommentId(UUID id, Authentication authentication) {
        return commentRepository.existsByCommentIdAndUser(
                id, userService.findByEmail(authentication.getName())
        );
    }
}
