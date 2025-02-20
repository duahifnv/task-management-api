package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.dto.TaskRequest;
import com.fizalise.taskmngr.dto.TaskResponse;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.exception.UserNotFoundException;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.repository.TaskRepository;
import com.fizalise.taskmngr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "Сервис управления задачами")
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    public List<Task> findAllTasks() {
        return taskRepository.findAll(
                Sort.by(Sort.Direction.DESC, "creationDate")
        );
    }
    public List<Task> findAllTasks(String executorEmail) {
        User taskExecutor = userRepository.findByEmail(executorEmail)
                .orElseThrow(UserNotFoundException::new);
        return taskRepository.findAllByExecutor(taskExecutor);
    }
    public Task findTask(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
    public Task createTask(TaskRequest taskRequest, String taskAuthorEmail) {
        User taskAuthor = userRepository.findByEmail(taskAuthorEmail)
                .orElseThrow(UserNotFoundException::new);
        Task createdTask = taskRepository.save(
                taskMapper.toTask(taskRequest, taskAuthor)
        );
        log.info("Создана задача: {}", createdTask);
        return createdTask;
    }
    public void removeTask(UUID id) {
        if (!taskRepository.existsByTaskId(id)) {
            throw new ResourceNotFoundException();
        }
        taskRepository.deleteById(id);
        log.info("Удалена задача с id: {}", id);
    }
}
