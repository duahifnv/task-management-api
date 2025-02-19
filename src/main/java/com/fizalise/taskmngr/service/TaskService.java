package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    public List<Task> findAllTasks() {
        return taskRepository.findAll(
                Sort.by(Sort.Direction.DESC, "creationDate")
        );
    }
}
