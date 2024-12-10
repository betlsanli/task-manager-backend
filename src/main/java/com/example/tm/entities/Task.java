package com.example.tm.entities;

import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Task extends BaseEntity {

    @Column(nullable = false, length = 128)
    private String title;

    @Column(length = 512)
    private String description;

    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @Builder.Default
    private Status status = Status.TO_DO;

    private LocalDateTime dueDate;

    @Column(insertable = false)
    private LocalDateTime completedAt;

    @Column(insertable = false)
    private LocalDateTime startedAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private List<AppUser> assignees = new ArrayList<>();

    @PrePersist
    private void onPersist() {
        this.project.addTask(this);
        for(AppUser assignee : assignees){
            assignee.addAssignedTask(this);
        }
    }
    @PreUpdate
    private void onUpdate() {
        for(AppUser assignee : assignees){
            assignee.addAssignedTask(this);
        }
    }
}
