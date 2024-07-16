package com.example.todo.controllers;


import com.example.todo.dto.AppUserDTO;
import com.example.todo.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class AppUserController {
    private final AppUserService appUserService;
//    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/{userId}")
    public AppUserDTO getUserById(@PathVariable UUID userId) {
        return appUserService.getById(userId);
    }

    @GetMapping("/all-users")
    public List<AppUserDTO> getAllUsers() {
        return appUserService.getAll();
    }

}
