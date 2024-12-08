package com.example.tm.dto.ProjectAssignment;

import com.example.tm.dto.AppUser.AppUserResponseDTO;

import java.util.UUID;


public record ProjectAssignmentResponseDTO(
        AppUserResponseDTO userDto,
        UUID projectId,
        String roleStr
) {}
