package com.example.todo.dto.request.create;


public record AppUserCreate(
        String email,
        String password,
        String firstName,
        String lastName
) {}
