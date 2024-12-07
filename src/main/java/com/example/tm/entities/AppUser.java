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
public class AppUser extends  BaseEntity{

    @Column(unique=true, nullable = false,length = 320)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 128)
    private String firstName;

    @Column(nullable = false, length = 128)
    private String lastName;

    @OneToMany(mappedBy = "manager")
    @Builder.Default
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private List<Project> managedProjects = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserTaskProject> assignments = new ArrayList<>();

    @PreUpdate
    public void onUpdate(){
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

    public void addManagedProject(Project project) {
        if(!managedProjects.contains(project))
            managedProjects.add(project);
    }

    public void removeManagedProject(Project project) {
        if(managedProjects.contains(project))
            managedProjects.remove(project);
    }
}
