package com.example.tm.controllers;

import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.dto.AppUser.AppUserRequestDTO;
import com.example.tm.dto.AppUser.LoginRequestDTO;
import com.example.tm.entities.AppUser;
import com.example.tm.repositories.AppUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository userRepository;
    private final AppUserMapper appUserMapper;
    private final AuthenticationManager authenticationManager;

    public AuthController(AppUserRepository userRepository, AppUserMapper appUserMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.appUserMapper = appUserMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }

        AppUser user = appUserMapper.toEntity(request);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO request, HttpSession session) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated user (AppUser from database)
        AppUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.email()));

        session.setAttribute("userId", user.getId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session, HttpServletResponse response) {
        // Invalidate the session to log out
        session.invalidate();

        // Clear the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        // Delete the JSESSIONID cookie
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0); // Set the cookie's expiration to 0 to delete it
        cookie.setPath("/"); // Make sure the path matches the cookie's original path
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}
