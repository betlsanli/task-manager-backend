package com.example.tm.repositories;

import com.example.tm.entities.Project;
import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    //List<Task> findByTitle(String title);
    List<Task> findAllByTitleContainingIgnoreCase(String title);
    List<Task> findAllByDescriptionContainingIgnoreCase(String description);

    List<Task> findAllByParentTaskIsNull();

    List<Task> findAllByBelongsToAndParentTaskIsNull(Project project);
    List<Task> findAllByBelongsToInAndParentTaskIsNull(List<Project> projects);
    List<Task> findAllByParentTask(Task parenTask);


    List<Task> findAllByPriority(Priority priority);
    List<Task> findAllByPriorityAndDueDate(Priority priority, LocalDateTime dueDate);

    List<Task> findAllByStatus(Status status);

    List<Task> findAllByDueDate(LocalDateTime dueDate);
    List<Task> findAllByDueDateBefore(LocalDateTime dueDate);
    List<Task> findAllByDueDateAfter(LocalDateTime dueDate);
    List<Task> findAllByDueDateBetween(LocalDateTime dueDateBefore, LocalDateTime dueDateAfter);

    List<Task> findAllByCompletedAtNotNull();
    List<Task> findAllByCompletedAtIsNull();

    List<Task> findAllByStartedAtIsNull();
    List<Task> findAllByStartedAtNotNull();

}
