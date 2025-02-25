package com.fizalise.taskmngr.controller;

import com.fizalise.taskmngr.dto.AuthenticationRequest;
import com.fizalise.taskmngr.dto.JwtResponse;
import com.fizalise.taskmngr.dto.RegistrationRequest;
import com.fizalise.taskmngr.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "Зарегистрировать нового пользователя",
            description = "Возвращает сгенерированный JWT-токен")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse registerNewUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return authService.registerNewUser(registrationRequest);
    }
    @Operation(summary = "Аутентифицировать существующего пользователя",
            description = "Возвращает сгенерированный JWT-токен")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authService.authenticate(authenticationRequest);
    }
}
