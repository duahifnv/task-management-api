package com.fizalise.taskmngr.service;

import com.fizalise.taskmngr.entity.Priority;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.ResourceNotFoundException;
import com.fizalise.taskmngr.repository.TaskRepository;
import com.fizalise.taskmngr.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class TaskServiceTest {
    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"));
    static final UUID taskId = UUID.fromString("9e976257-f60b-418e-ae39-e34bb78d9e58");
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;
    User taskAuthor;
    Authentication authentication;
    @BeforeAll
    static void startContainer() {
        postgreSQLContainer.start();
    }
    @BeforeEach
    void setupContainerData() {
        taskRepository.deleteAll();
        taskAuthor = userRepository.findAll().getFirst();
        try {
            Task task = Task.builder()
                    .taskId(taskId)
                    .label("Task label")
                    .description("Some task description")
                    .status(Status.IN_PROGRESS)
                    .priority(Priority.MIDDLE)
                    .author(taskAuthor)
                    .creationDate(Date.valueOf(LocalDate.now()))
                    .build();
            taskRepository.save(task);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Не найдено ни одного пользователя");
        }
    }
    @BeforeEach
    void setAuthorities() {
        authentication = mock(Authentication.class);
        when(authentication.getCredentials())
                .thenReturn(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    @Test
    void findAllTasks() {
        var allTasks = taskService.findAllTasks(authentication);
        assertEquals(allTasks.getFirst().getTaskId(), taskId);
        assertEquals(allTasks.size(), 1);
    }
    @Test
    void findTask() {
        var task = taskService.findTask(taskId, authentication);
        assertNotNull(task);
    }
    @Test
    void findTask_throwsResourceNotFoundException() {
        taskRepository.deleteAll();
        assertThrows(ResourceNotFoundException.class,
                () -> taskService.findTask(taskId, authentication)
        );
    }
}