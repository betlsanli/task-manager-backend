package com.example.tm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AppUser extends  BaseEntity{

    @Column(unique=true, nullable = false,length = 320)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 128)
    private String firstName;

    @Column(nullable = false, length = 128)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectAssignment> projectAssignments = new ArrayList<>();

    @ManyToMany(mappedBy = "assignees", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Task> assignedTasks = new ArrayList<>();


    @PreUpdate
    public void onUpdate(){
        this.setLastModifiedAt(LocalDateTime.now());
    }

    public void addAssignedTask(Task task){
        if(!this.assignedTasks.contains(task)){
            this.assignedTasks.add(task);
        }
    }
    public void removeAssignedTask(Task task){
        if(this.assignedTasks.contains(task)){
            this.assignedTasks.remove(task);
        }
    }

    public void addProjectAssignment(ProjectAssignment projectAssignment){
        if(!projectAssignments.contains(projectAssignment)){
            projectAssignments.add(projectAssignment);
        }
    }

    public void removeProjectAssignment(ProjectAssignment projectAssignment){
        if(projectAssignments.contains(projectAssignment)){
            projectAssignments.remove(projectAssignment);
        }
    }

}
