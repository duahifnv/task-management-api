package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.exception.UnableToStartExecutionException;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.exception.UnableToStopExecutionException;
import com.fizalise.taskmngr.repository.ExecutionRepository;
import com.fizalise.taskmngr.repository.sort.ExecutionSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    public List<Execution> findAll() {
        return executionRepository.findAll(ExecutionSort.START_DESC.getSort());
    }
    public Execution findExecution(UUID taskId, UUID userId) {
        return executionRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(ResourceNotFoundException::new);
    }
    @Transactional
    public void updateExecutionStatus(UUID taskId, UUID userId, Status status) {
        Execution execution = findExecution(taskId, userId);
        if (status == Status.IN_PROGRESS && execution.getStatus() != Status.PENDING) {
            throw new UnableToStartExecutionException();
        }
        if (status == Status.DONE && execution.getStatus() != Status.IN_PROGRESS) {
            throw new UnableToStopExecutionException();
        }
        if (status == Status.IN_PROGRESS) {
            execution.setExecutionStart(
                    Timestamp.valueOf(LocalDateTime.now())
            );
        }
        execution.setStatus(status);
        executionRepository.save(execution);
    }
    public boolean isTaskDone(UUID taskId) {
        return getExecutionsCount(taskId, Status.DONE) == getExecutionsCount(taskId);
    }
    private int getExecutionsCount(UUID taskId) {
        return executionRepository.countByTaskId(taskId);
    }
    private int getExecutionsCount(UUID taskId, Status status) {
        return executionRepository.countByTaskIdAndStatusEquals(taskId, status);
    }
}
