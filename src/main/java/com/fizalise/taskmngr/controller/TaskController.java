package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.task.TaskResponse;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    @GetMapping
    public List<TaskResponse> getAllTasks(Principal principal) {
        return taskMapper.toResponses(
                taskService.findAllTasks(principal.getName())
        );
    }
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable UUID id, Principal principal) {
        return taskMapper.toResponse(
                taskService.findTask(id, principal.getName())
        );
    }
    @PutMapping("/{id}")
    public TaskResponse setTaskStatus(@PathVariable UUID id, @RequestParam Status status,
                                      Principal principal) {
        return taskMapper.toResponse(
                taskService.updateTaskStatus(id, status, principal.getName())
        );
    }
}
