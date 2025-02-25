package com.fizalise.taskmngr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank @Email
                                    @Schema(description = "Почта", format = "email@domen.xx",
                                            example = "mail@mail.ru") String email,
                                    @NotBlank
                                    @Schema(description = "Пароль", minLength = 4,
                                            maxLength = 30, example = "3afl3ajf") String password) {}
