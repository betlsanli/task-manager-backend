package com.example.todo.entities;

import com.example.todo.enums.Priority.Priority;
import com.example.todo.enums.Status.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private List<AppUser> assignees = new ArrayList<>();

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Task> subTasks = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task parentTask;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tasklist belongsTo;

    @PrePersist
    private void onCreate() {
        belongsTo.addTask(this);
        if(parentTask != null)
            parentTask.addSubTask(this);
        for(AppUser assignee : assignees){
            assignee.addAssignedTask(this);
        }
    }
    @PreUpdate
    private void onUpdate() {
        if(parentTask != null)
            parentTask.addSubTask(this);
        for(AppUser assignee : assignees){
            assignee.addAssignedTask(this);
        }
    }

    public void addSubTask(Task subTask) {
        if (!subTasks.contains(subTask)) {
            this.subTasks.add(subTask);
        }
    }
    public void removeSubTask(Task subTask) {
        if (subTasks.contains(subTask)) {
            this.subTasks.remove(subTask);
        }
    }
}
