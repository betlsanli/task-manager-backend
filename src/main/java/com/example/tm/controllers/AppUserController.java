package com.example.tm.controllers;

import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.dto.AppUser.AppUserRequestDTO;
import com.example.tm.dto.AppUser.AppUserResponseDTO;
import com.example.tm.security.CustomUserDetails;
import com.example.tm.services.AppUserService;
import jakarta.servlet.http.HttpSession;
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
public class AppUserController{
    private final AppUserService appUserService;
    private static final Logger log =  LoggerFactory.getLogger(AppUserController.class);
    private final AppUserMapper appUserMapper;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserMapper appUserMapper) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
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

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")//allow for admin only
    @GetMapping("/all-users")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers(HttpSession session) {
        try {
            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(appUserService.getAll());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-total-count")
    public ResponseEntity<Long> getTotalCount(HttpSession session) {
        try {
            if (session == null || session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.OK).body(appUserService.getTotalCount());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/profile")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<AppUserResponseDTO> getProfile(HttpSession session) {
        try {
            if (session == null || session.getAttribute("user") == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            return ResponseEntity.status(HttpStatus.OK).body(appUserMapper.toDto(userDetails));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId, HttpSession session) {
        try {
            if (userId == null)
                throw new IllegalArgumentException("User id cannot be null");

            // Check if the session is invalid or doesn't contain the user

            if (session == null || session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found or user not logged in");
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.getUserId() == null || !userDetails.getUserId().equals(userId) || !userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User id not found or does not match");
            }

            boolean isDeleted = appUserService.deleteById(userId);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        } catch (IllegalArgumentException iae) {
            log.error(iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        } catch (NoSuchElementException nse) {
            log.error(nse.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable UUID userId, @RequestBody @Valid AppUserRequestDTO updateUserRequest, HttpSession session) {
        try {
            if (userId == null || updateUserRequest == null)
                throw new IllegalArgumentException("Parameters cannot be null");

            // Check if the session is invalid or doesn't contain the user

            if (session == null || session.getAttribute("user") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            CustomUserDetails userDetails = (CustomUserDetails) session.getAttribute("user");
            if (userDetails.getUserId() == null || !userDetails.getUserId().equals(userId) || !userDetails.hasAuthority("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
