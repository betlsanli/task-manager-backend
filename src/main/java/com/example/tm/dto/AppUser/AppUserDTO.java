package com.example.tm.dto.AppUser;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppUserDTO(
        @NotEmpty(message = "Email cannot be null or empty")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        String password,
        @NotEmpty(message = "First name cannot be null or empty")
        String firstName,
        @NotEmpty(message = "Last name cannot be null or empty")
        String lastName,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
        LocalDateTime lastModifiedAt
) {}