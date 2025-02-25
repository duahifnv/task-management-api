package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.repository.ExecutionRepository;
import com.fizalise.taskmngr.repository.sort.ExecutionSort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    public List<Execution> findAll() {
        return executionRepository.findAll(ExecutionSort.START_DESC.getSort());
    }
}
