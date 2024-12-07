package com.example.tm.entities;

import com.example.tm.enums.Priority.Priority;
import com.example.tm.enums.Status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserTaskProject> assignments = new ArrayList<>();

    @PreUpdate
    public void onUpdate() {
        this.setLastModifiedAt(LocalDateTime.now());
    }

    public void addAssignment(UserTaskProject assignment) {
        if(!assignments.contains(assignment))
            assignments.add(assignment);
    }
    public void removeAssignment(UserTaskProject assignment) {
        if(assignments.contains(assignment))
            assignments.remove(assignment);
    }
}
