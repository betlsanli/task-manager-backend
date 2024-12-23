package com.example.tm.repositories;

import com.example.tm.dto.Project.ProjectTaskPriorityCountDTO;
import com.example.tm.dto.Project.ProjectTaskStatusCountDTO;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.entities.Task;
import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    //List<Task> findByTitle(String title);
    List<Task> findAllByTitleContainingIgnoreCase(String title);
    List<Task> findAllByDescriptionContainingIgnoreCase(String description);

    List<Task> findAllByPriority(Priority priority);
    List<Task> findAllByPriorityAndDueDate(Priority priority, LocalDateTime dueDate);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
    List<Task> findTasksByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT t FROM Task t JOIN t.assignees a WHERE a.id = :userId")
    List<Task> findTasksByAssigneeId(@Param("userId") UUID userId);

    List<Task> findAllByProject(Project project);


    List<Task> findAllByAssigneesContaining(AppUser user);

    List<Task> findAllByStatus(Status status);

    List<Task> findAllByDueDate(LocalDateTime dueDate);
    List<Task> findAllByDueDateBefore(LocalDateTime dueDate);
    List<Task> findAllByDueDateAfter(LocalDateTime dueDate);
    List<Task> findAllByDueDateBetween(LocalDateTime dueDateBefore, LocalDateTime dueDateAfter);

    List<Task> findAllByCompletedAtNotNull();
    List<Task> findAllByCompletedAtIsNull();

    List<Task> findAllByStartedAtIsNull();
    List<Task> findAllByStartedAtNotNull();

    long count();

    long countAllByProjectId(UUID projectId);

    long countAllByUserId(UUID id);

    @Query("SELECT new com.example.tm.dto.Project.ProjectTaskStatusCountDTO(t.status, COUNT(t)) " +
            "FROM Task t " +
            "WHERE t.project.id = :projectId " +
            "GROUP BY t.status")
    List<ProjectTaskStatusCountDTO> findTaskCountByProjectIdGroupedByStatus(UUID projectId);

    @Query("SELECT new com.example.tm.dto.Project.ProjectTaskPriorityCountDTO(t.priority, COUNT(t)) " +
            "FROM Task t " +
            "WHERE t.project.id = :projectId " +
            "GROUP BY t.priority")
    List<ProjectTaskPriorityCountDTO> findTaskCountByProjectIdGroupedByPriority(UUID projectId);

}
