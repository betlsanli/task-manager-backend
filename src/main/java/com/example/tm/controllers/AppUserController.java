package com.example.tm.controllers;

import com.example.tm.dto.AppUser.AppUserRequestDTO;
import com.example.tm.dto.AppUser.AppUserResponseDTO;
import com.example.tm.services.AppUserService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
public class AppUserController {
    private final AppUserService appUserService;
    private static final Logger log =  LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable UUID userId) {
        try {
            if (userId == null)
                throw new IllegalArgumentException("User id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(appUserService.getById(userId));
        }catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (NoSuchElementException nsee) {
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAll());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/create-user")
    public ResponseEntity<AppUserResponseDTO> createUser(@RequestBody @Valid AppUserRequestDTO newUserRequest) {
        try {
            if (newUserRequest == null)
                throw new IllegalArgumentException("New user request cannot be null");
            AppUserResponseDTO createdUser = appUserService.createUser(newUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }catch(IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (NoSuchElementException nsee) {
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID userId) {
        try {
            if (userId == null)
                throw new IllegalArgumentException("User id cannot be null");
            boolean isDeleted = appUserService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(isDeleted);

        }catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }catch (NoSuchElementException nse) {
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable UUID userId, @RequestBody @Valid AppUserRequestDTO updateUserRequest) {
        try {
            if (userId == null || updateUserRequest == null)
                throw new IllegalArgumentException("Parameters cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.updateUser(userId, updateUserRequest));
        }catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (NoSuchElementException nsee) {
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
