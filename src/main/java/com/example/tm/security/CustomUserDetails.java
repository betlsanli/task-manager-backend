package com.example.tm.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UUID userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UUID userId, String firstName, String lastName, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getUsername() {
        return firstName + lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Set logic for account expiration if required
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Set logic for account locking if required
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Set logic for credential expiration if required
    }

    @Override
    public boolean isEnabled() {
        return true; // Set logic for enabling/disabling accounts if required
    }

    public boolean hasAuthority(String str_authority) {
        if(authorities.stream().anyMatch(authority -> authority.getAuthority().equals(str_authority)))
            return true;
        return false;
    }
}
