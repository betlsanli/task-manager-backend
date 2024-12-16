package com.example.tm.dto.AppUser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotEmpty(message = "Email cannot be null or empty")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        String password
) {}