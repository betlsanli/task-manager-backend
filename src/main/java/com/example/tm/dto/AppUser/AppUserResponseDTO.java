package com.example.tm.dto.AppUser;

import java.util.UUID;

public record AppUserResponseDTO(
        UUID userId,
        String email,
        String firstName,
        String lastName
) {}
