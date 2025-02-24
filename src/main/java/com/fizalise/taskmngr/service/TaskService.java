package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.dto.task.TaskRequest;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.mapper.TaskMapper;
import com.fizalise.taskmngr.repository.TaskRepository;
import com.fizalise.taskmngr.repository.sort.TaskSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "Сервис управления задачами")
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final AuthService authService;
    public Page<Task> findAllTasks(Integer page, UUID authorId, UUID executorId,
                                   Authentication authentication) {
        var pageRequest = getPageRequest(page);
        if (!authService.hasAdminRole(authentication)) {
            return taskRepository.findAllByExecutor(
                    findUser(authentication.getName()), pageRequest
            );
        }
        return taskRepository.findAllFiltered(authorId, executorId, pageRequest);
    }
    public Task findTask(UUID id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (!authService.hasAdminRole(authentication) &&
                !task.getExecutorList().contains(findUser(authentication.getName()))
        ) {
            log.info("Попытка доступа к чужой задаче от пользователя {}", authentication.getName());
            throw new ResourceNotFoundException();
        }
        return task;
    }
    public List<User> findTaskExecutors(UUID id, Authentication authentication) {
        Task task = findTask(id, authentication);
        return task.getExecutorList().stream().toList();
    }
    @Transactional
    public Task createTask(TaskRequest taskRequest, Authentication authentication) {
        Task createdTask = taskRepository.save(
                taskMapper.toTask(taskRequest, findUser(authentication.getName()))
        );
        log.info("Создана задача: {}", createdTask);
        return createdTask;
    }
    @Transactional
    public Task updateTask(UUID id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        task = taskRepository.save(
                taskMapper.toTask(task, taskRequest)
        );
        log.info("Изменена задача: {}", task);
        return task;
    }
    @Transactional
    public Task updateTaskStatus(UUID id, Status status, Authentication authentication) {
        Task task = findTask(id, authentication);
        task.setStatus(status);
        taskRepository.save(task);
        log.info("Изменен статус задачи {}: {}", id, status);
        return task;
    }
    @Transactional
    public void removeTask(UUID id) {
        if (!taskRepository.existsByTaskId(id)) {
            throw new ResourceNotFoundException();
        }
        taskRepository.deleteById(id);
        log.info("Удалена задача с id: {}", id);
    }
    private User findUser(String email) {
        return userService.getUserByEmail(email);
    }
    private PageRequest getPageRequest(Integer page) {
        return PageRequest.of(page, TaskRepository.PAGE_SIZE, TaskSort.DATE_DESC.getSort());
    }
}
