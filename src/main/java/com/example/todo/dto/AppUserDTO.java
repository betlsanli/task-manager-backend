package com.example.todo.dto;

import com.example.todo.entities.Tasklist;

import java.util.List;
import java.util.UUID;

public record AppUserDTO(
       UUID id,
       String firstName,
       String lastName,
       String email,
       List<TasklistDTO> tasklists
) {}
