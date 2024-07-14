package com.example.todo.repositories;

import com.example.todo.entities.Priority;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    //List<Task> findByTitle(String title);
    List<Task> findAllByTitleContainingIgnoreCase(String title);
    List<Task> findAllByDescriptionContainingIgnoreCase(String description);

    List<Task> findAllByBelongsTo(Tasklist tasklist);
    List<Task> findAllByBelongsToIn(List<Tasklist> tasklists);
    List<Task> findAllByParentTask(Task parenTask);

    Optional<Task> findById(UUID id);

    List<Task> findAllByPriority(Priority priority);
    List<Task> findAllByPriorityAndDueDate(Priority priority, LocalDateTime dueDate);

    List<Task> findAllByDueDate(LocalDateTime dueDate);
    List<Task> findAllByDueDateBefore(LocalDateTime dueDate);
    List<Task> findAllByDueDateAfter(LocalDateTime dueDate);
    List<Task> findAllByDueDateBetween(LocalDateTime dueDateBefore, LocalDateTime dueDateAfter);

    List<Task> findAllByCompletedAtNotNull();
    List<Task> findAllByCompletedAtIsNull();

    List<Task> findAllByStartedAtIsNull();
    List<Task> findAllByStartedAtNotNull();

}
