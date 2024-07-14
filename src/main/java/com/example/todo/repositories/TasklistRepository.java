package com.example.todo.repositories;

import com.example.todo.entities.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TasklistRepository extends JpaRepository<Tasklist, UUID> {

    Optional<Tasklist> findById(UUID id);

    List<Tasklist> findByTitle(String title);
    List<Tasklist> findAllByTitleContainingIgnoreCase(String title);

    List<Tasklist> findAllByDescriptionContainingIgnoreCase(String description);

}
