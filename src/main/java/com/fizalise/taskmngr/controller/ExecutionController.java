package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.service.ExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/executions")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;
    @Operation(summary = "Получить список всех исполнений задач")
    @GetMapping
    public List<Execution> getAllExecutions(@RequestParam(defaultValue = "0") @Min(0) Integer page) {
        return executionService.findAllExecutions(page).stream().toList();
    }
}
