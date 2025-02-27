package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.user.UserResponse;
import com.fizalise.taskmngr.mapper.UserMapper;
import com.fizalise.taskmngr.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) Integer page) {
        return userMapper.toResponses(
                userService.findAllUsers(page)
        );
    }
    @Operation(summary = "Получить пользователя по id")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable UUID id) {
        return userMapper.toResponse(userService.findById(id));
    }
    @Operation(summary = "Получить пользователя по email")
    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable String email) {
        return userMapper.toResponse(userService.findByEmail(email));
    }
    @Operation(summary = "Удалить пользователя по id")
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
