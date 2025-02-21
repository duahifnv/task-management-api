package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.task.TaskRequest;
import com.fizalise.taskmngr.dto.task.TaskResponse;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks/crud")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class TaskControllerCrud {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> getAllTasks() {
        return taskMapper.toResponses(taskService.findAllTasks());
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable UUID id) {
        return taskMapper.toResponse(
                taskService.findTask(id)
        );
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse addTask(@Valid @RequestBody TaskRequest taskRequest, Principal principal) {
        return taskMapper.toResponse(
                taskService.createTask(taskRequest, principal.getName())
        );
    }
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable UUID id, @Valid @RequestBody TaskRequest taskRequest) {
        return taskMapper.toResponse(
                taskService.updateTask(id, taskRequest)
        );
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable UUID id) {
        taskService.removeTask(id);
    }
}
