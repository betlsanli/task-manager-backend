package com.example.tm.repositories;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAllByFirstNameContainingIgnoreCase(String firstName);
    List<AppUser> findAllByLastNameContainingIgnoreCase(String lastName);
    List<AppUser> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<AppUser> findAllByProjectContains(Project project);
}
