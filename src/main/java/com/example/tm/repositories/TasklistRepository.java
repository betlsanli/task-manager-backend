package com.example.tm.repositories;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Task;
import com.example.tm.entities.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TasklistRepository extends JpaRepository<Tasklist, UUID> {

    List<Tasklist> findByTitle(String title);
    List<Tasklist> findAllByTitleContainingIgnoreCase(String title);

    List<Tasklist> findAllByDescriptionContainingIgnoreCase(String description);

    List<Tasklist> findAllByUsersContains(AppUser user);
    Optional<Tasklist> findByTasksContains(Task task);
}
