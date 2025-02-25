package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.repository.ExecutionRepository;
import com.fizalise.taskmngr.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/executions")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;
    @GetMapping
    public List<Execution> getAll() {
        return executionService.findAll();
    }
}
