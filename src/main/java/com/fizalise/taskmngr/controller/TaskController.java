package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.task.TaskResponse;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
