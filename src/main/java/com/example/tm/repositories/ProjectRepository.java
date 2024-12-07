package com.example.tm.repositories;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByTitle(String title);
    List<Project> findAllByTitleContainingIgnoreCase(String title);

    List<Project> findAllByDescriptionContainingIgnoreCase(String description);

    List<Project> findAllByUsersContains(AppUser user);
    Optional<Project> findByTasksContains(Task task);
}
