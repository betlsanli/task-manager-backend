package com.example.todo.controllers;

import com.example.todo.dto.request.create.AppUserCreate;
import com.example.todo.entities.AppUser;
import com.example.todo.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public AppUser getUserById(@PathVariable UUID userId) {
        return appUserService.getById(userId);
    }

    @GetMapping("/all-users")
    public List<AppUser> getAllUsers() {
        return appUserService.getAll();
    }

    @PostMapping("/create-user")
    public AppUser createUser(@RequestBody AppUserCreate newUserRequest) {
        return appUserService.createUser(newUserRequest);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        appUserService.deleteById(userId);
    }

}
