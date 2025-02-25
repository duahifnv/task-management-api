package com.fizalise.taskmngr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(@NotBlank @Email
                                  @Schema(description = "Почта", format = "email@domen.xx",
                                          example = "mail@mail.ru") String email,
                                  @NotBlank @Length(min = 4, max = 30)
                                  @Schema(description = "Пароль от 4 до 30 символов", minLength = 4,
                                          maxLength = 30, example = "3afl3ajf")
                                  String password,
                                  @NotBlank @Schema(description = "Имя пользователя", example = "Юзер")
                                  String name,
                                  @NotBlank @Schema(description = "Фамилия пользователя", example = "Юзернеймов")
                                  String surname) {}