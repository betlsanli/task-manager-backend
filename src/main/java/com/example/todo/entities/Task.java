package com.example.todo.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Priority priority = Priority.Orta;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.Yapılacak;

    private LocalDateTime dueDate;

    @Column(insertable = false)
    private LocalDateTime completedAt;

    @Column(insertable = false)
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "parentTask")
    private List<Task> subTasks;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task parentTask;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tasklist belongsTo;
}
