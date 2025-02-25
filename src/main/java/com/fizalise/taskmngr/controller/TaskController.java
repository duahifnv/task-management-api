package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.comment.CommentResponse;
import com.fizalise.taskmngr.dto.task.TaskRequest;
import com.fizalise.taskmngr.dto.task.TaskResponse;
import com.fizalise.taskmngr.dto.user.UserResponse;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.mapper.CommentMapper;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.mapper.UserMapper;
import com.fizalise.taskmngr.service.CommentService;
import com.fizalise.taskmngr.service.TaskService;
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
@RequestMapping("/tasks")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @Operation(summary = "Получить список всех задач")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> getAllTasks(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                          Authentication authentication) {
        return taskMapper.toResponses(
                taskService.findAllTasks(page, authentication)
        );
    }
    @Operation(summary = "Получить список исполнителей задачи")
    @GetMapping("/{id}/executors")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getTaskExecutors(@PathVariable UUID id,
                                               @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                               Authentication authentication) {
        return userMapper.toResponses(
                taskService.findTaskExecutors(id, page, authentication)
        );
    }
    @Operation(summary = "Получить список комментариев к задаче")
    @GetMapping("/{id}/comments")
    public List<CommentResponse> getTaskComments(@PathVariable UUID id,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                 Authentication authentication) {
        return commentMapper.toResponses(
                commentService.findAllCommentsByTask(id, page, authentication)
        );
    }
    @Operation(summary = "Получить задачу")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable UUID id, Authentication authentication) {
        return taskMapper.toResponse(
                taskService.findTask(id, authentication)
        );
    }
    @Operation(summary = "Добавить новую задачу")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse addTask(@Valid @RequestBody TaskRequest taskRequest,
                                Authentication authentication) {
        return taskMapper.toResponse(
                taskService.createTask(taskRequest, authentication)
        );
    }
    @Operation(summary = "Обновить существующую задачу")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable UUID id,
                                   @Valid @RequestBody TaskRequest taskRequest) {
        return taskMapper.toResponse(
                taskService.updateTask(id, taskRequest)
        );
    }
    @Operation(summary = "Начать выполнение задачи")
    @PutMapping("/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    public void startTask(@PathVariable UUID id, Authentication authentication) {
        taskService.startExecution(id, authentication);
    }
    @Operation(summary = "Закончить выполнение задачи")
    @PutMapping("/{id}/stop")
    @ResponseStatus(HttpStatus.OK)
    public void stopTask(@PathVariable UUID id, Authentication authentication) {
        taskService.stopExecution(id, authentication);
    }
    @Operation(summary = "Установить статус задачи")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/")
    public TaskResponse setTaskStatus(@PathVariable UUID id, @RequestParam Status status,
                                      Authentication authentication) {
        return taskMapper.toResponse(
                taskService.updateTaskStatus(id, status, authentication)
        );
    }
    @Operation(summary = "Удалить задачу")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable UUID id) {
        taskService.removeTask(id);
    }
}
