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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ExecutionService executionService;
    public Page<Task> findAllTasks(Integer page, Authentication authentication) {
        var pageRequest = getPageRequest(page);
        if (!authService.hasAdminRole(authentication)) {
            return taskRepository.findAllByExecutor(
                    findUser(authentication.getName()), pageRequest
            );
        }
        return taskRepository.findAll(pageRequest);
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
    public Page<User> findTaskExecutors(UUID id, Integer page, Authentication authentication) {
        Task task = findTask(id, authentication);
        return getPage(task.getExecutorList().stream().toList(), getPageRequest(page));
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
        if (task.getStatus() != status) {
            task.setStatus(status);
            taskRepository.save(task);
            log.info("Изменен статус задачи {}: {}", id, status);
        }
        return task;
    }
    @Transactional
    public void startExecution(UUID id, Authentication authentication) {
        UUID taskId = findTask(id, authentication)
                .getTaskId();
        UUID userId = userService.findByEmail(authentication.getName())
                .getUserId();
        executionService.updateExecutionStatus(taskId, userId, Status.IN_PROGRESS);
        log.info("Начато выполнение задачи {} пользователем {}", taskId, userId);
        updateTaskStatus(taskId, Status.IN_PROGRESS, authentication);
    }
    @Transactional
    public void stopExecution(UUID id, Authentication authentication) {
        UUID taskId = findTask(id, authentication)
                .getTaskId();
        UUID userId = userService.findByEmail(authentication.getName())
                .getUserId();
        executionService.updateExecutionStatus(taskId, userId, Status.DONE);
        log.info("Завершено выполнение задачи {} пользователем {}", taskId, userId);
        if (executionService.isTaskDone(taskId)) {
            updateTaskStatus(taskId, Status.DONE, authentication);
        }
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
        return userService.findByEmail(email);
    }
    private PageRequest getPageRequest(Integer page) {
        return PageRequest.of(page, TaskRepository.PAGE_SIZE, TaskSort.DATE_DESC.getSort());
    }
    private <T> Page<T> getPage(List<T> items, Pageable pageable) {
        int fromIndex = (int) pageable.getOffset();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), items.size());
        List<T> pageItems = items.subList(fromIndex, toIndex);

        return new PageImpl<>(pageItems, pageable, items.size());
    }
}
