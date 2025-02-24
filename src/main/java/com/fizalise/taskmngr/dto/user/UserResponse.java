package com.fizalise.taskmngr.dto.user;

import java.util.UUID;

public record UserResponse(UUID userId, String email, String name, String surname) {
}
