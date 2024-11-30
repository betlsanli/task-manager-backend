package com.example.tm.controllers;

import com.example.tm.dto.request.create.AppUserCreate;
import com.example.tm.dto.request.update.AppUserUpdate;
import com.example.tm.entities.AppUser;
import com.example.tm.services.AppUserService;
import com.example.tm.services.create.AppUserCreateService;
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
    private final AppUserCreateService appUserCreateService;
    private static final Logger log =  LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserCreateService appUserCreateService) {
        this.appUserService = appUserService;
        this.appUserCreateService = appUserCreateService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getUserById(@PathVariable UUID userId) {
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
    public ResponseEntity<List<AppUser>> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAll());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/of-list/{listId}")
    public ResponseEntity<List<AppUser>> getAllUsersOfList(@PathVariable UUID listId){
        try {
            if (listId == null)
                throw new IllegalArgumentException("List id cannot be null");
            return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAllByList(listId));
        }catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (NoSuchElementException nsee) {
            log.error(nsee.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<AppUser> createUser(@RequestBody @Valid AppUserCreate newUserRequest) {
        try {
            if (newUserRequest == null)
                throw new IllegalArgumentException("New user request cannot be null");
            AppUser createdUser = appUserCreateService.createUser(newUserRequest);
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
    public ResponseEntity<AppUser> updateUser(@PathVariable UUID userId, @RequestBody @Valid AppUserUpdate updateUserRequest) {
        try {
            if (userId == null || updateUserRequest == null)
                throw new IllegalArgumentException("Parameters cannot be null");
            return ResponseEntity.status(HttpStatus.CREATED).body(appUserCreateService.updateUser(userId, updateUserRequest));
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
