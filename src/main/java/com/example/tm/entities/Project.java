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

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private AppUser manager;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "project_user",  // The name of the junction table
            joinColumns = @JoinColumn(name = "project_id"),  // Foreign key for the Project
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Foreign key for the AppUser
    )
    private List<AppUser> users = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserTaskProject> assignments = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        manager.addManagedProject(this);
    }
    @PreUpdate
    protected void onUpdate() { // do not forget to remove old in controller
        manager.addManagedProject(this);
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
