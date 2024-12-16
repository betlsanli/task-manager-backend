package com.example.tm.controllers;

import com.example.tm.dto.AppUser.AppUserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AppUserControllerInt {
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers();
}
