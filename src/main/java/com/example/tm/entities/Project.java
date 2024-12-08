package com.example.tm.entities;

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
public class Project extends BaseEntity{

    @Column(nullable = false, length = 126)
    private String title;

    @Column(length = 512)
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectAssignment> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    public void addTeamMember(ProjectAssignment teamMember) {
        if (!teamMembers.contains(teamMember)) {
            teamMembers.add(teamMember);
        }
    }
    public void removeTeamMember(ProjectAssignment teamMember) {
        if (teamMembers.contains(teamMember)) {
            teamMembers.remove(teamMember);
        }
    }
    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
    }
    public void removeTask(Task task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.setLastModifiedAt(LocalDateTime.now());
    }

}
