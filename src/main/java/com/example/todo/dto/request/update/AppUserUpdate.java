package com.example.todo.dto.request.update;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AppUserUpdate(
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate lastModifiedAt,
        List<UUID> listIds
) {}
