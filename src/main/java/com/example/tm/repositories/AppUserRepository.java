package com.example.tm.repositories;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<AppUser> findById(UUID id);
    List<AppUser> findAllByFirstNameContainingIgnoreCase(String firstName);
    List<AppUser> findAllByLastNameContainingIgnoreCase(String lastName);
    List<AppUser> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<AppUser> findAllByAssignedTasksContaining(Task Task);
    long count();
}
