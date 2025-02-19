package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.repository.TaskRepository;
import com.fizalise.taskmngr.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TaskServiceTest {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"));
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;
    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
    }
    @BeforeEach
    void setupContainerData() {
        taskRepository.deleteAll();
        try {
            User user = userRepository.findAll().getFirst();
            Task task = Task.builder()
                    .taskId(UUID.fromString("9e976257-f60b-418e-ae39-e34bb78d9e58"))
                    .label("Task label")
                    .description("Some task description")
                    .status(Status.IN_PROGRESS)
                    .priority(Priority.MIDDLE)
                    .author(user)
                    .creationDate(Date.valueOf(LocalDate.now()))
                    .build();
            taskRepository.save(task);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Не найдено ни одного пользователя");
        }
    }
    @Test
    void findAllTasks() {
        var allTasks = taskService.findAllTasks();
        assertEquals(allTasks.getFirst().getTaskId(),
                UUID.fromString("9e976257-f60b-418e-ae39-e34bb78d9e58"));
        assertEquals(allTasks.size(), 1);
    }
    @Test
    void findTask() {
        var task = taskService.findTask(UUID.fromString("9e976257-f60b-418e-ae39-e34bb78d9e58"));
        assertNotNull(task);
    }
    @Test
    void findTask_throwsResourceNotFoundException() {
        taskRepository.deleteAll();
        assertThrows(ResourceNotFoundException.class,
                () -> taskService.findTask(
                        UUID.fromString("9e976257-f60b-418e-ae39-e34bb78d9e58"))
        );
    }
}