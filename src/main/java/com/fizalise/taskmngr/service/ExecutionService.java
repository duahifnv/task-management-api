package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.exception.UnableToStartExecutionException;
import com.fizalise.taskmngr.exception.UnableToStopExecutionException;
import com.fizalise.taskmngr.repository.ExecutionRepository;
import com.fizalise.taskmngr.repository.sort.ExecutionSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    public Page<Execution> findAllExecutions(Integer page) {
        return executionRepository.findAll(getPageRequest(page));
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
    private PageRequest getPageRequest(Integer page) {
        return PageRequest.of(page, ExecutionRepository.PAGE_SIZE, ExecutionSort.START_DESC.getSort());
    }
}
