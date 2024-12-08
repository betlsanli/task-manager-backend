package com.example.tm.dto.AppUser;

import jakarta.validation.constraints.NotEmpty;

public record AppUserRequestDTO(
        @NotEmpty(message = "Email cannot be null or empty")
        String email,
        @NotEmpty(message = "Password cannot be null or empty")
        String password,
        @NotEmpty(message = "First name cannot be null or empty")
        String firstName,
        @NotEmpty(message = "Last name cannot be null or empty")
        String lastName
) {}