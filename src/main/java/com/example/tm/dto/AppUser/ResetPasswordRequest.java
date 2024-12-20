package com.example.tm.dto.AppUser;

import java.util.UUID;

public record ResetPasswordRequest(
        UUID userId,
        String currentPassword,
        String newPassword
) {}
