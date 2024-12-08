package com.example.tm.dto.ProjectAssignment;

import com.example.tm.dto.AppUser.AppUserDTO;


public record ProjectAssignmentResponseDTO(
        AppUserDTO userDto,
        String roleStr
) {}
