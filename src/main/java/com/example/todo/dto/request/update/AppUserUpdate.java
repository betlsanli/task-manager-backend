package com.example.todo.dto.request.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AppUserUpdate(
        @NotEmpty(message = "Email cannot be null or empty")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        String password,
        @NotEmpty(message = "First name cannot be null or empty")
        String firstName,
        @NotEmpty(message = "Last name cannot be null or empty")
        String lastName,
        @NotNull
        List<UUID> listIds
) {}
