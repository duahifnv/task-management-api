package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.TaskRequest;
import com.fizalise.taskmngr.dto.TaskResponse;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
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
    public TaskResponse addTask(@RequestBody TaskRequest taskRequest, Principal principal) {
        return taskMapper.toResponse(
                taskService.createTask(taskRequest, principal.getName())
        );
    }
}
