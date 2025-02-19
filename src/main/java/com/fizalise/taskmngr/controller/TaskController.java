package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.TaskResponse;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskMapper.toResponses(taskService.findAllTasks());
    }
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable UUID id) {
        return taskMapper.toResponse(
                taskService.findTask(id)
        );
    }
}
