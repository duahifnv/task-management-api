package com.fizalise.taskmngr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(@NotBlank @Email String email,
                                  @NotBlank @Length(min = 4, max = 30) String password,
                                  @NotBlank String name,
                                  @NotBlank String surname) {}