package com.example.tm.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class ProjectAssignment {
    @EmbeddedId
    private UserProjectRoleID id;

    @ManyToOne
    @MapsId("userId")
    private AppUser user;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @Builder.Default
    @Column(updatable = false, nullable = false, insertable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;

    @PrePersist
    public void onPersist(){
        user.addProjectAssignment(this);
        project.addTeamMember(this);
    }

    @PreUpdate
    public void preUpdate() {
        user.addProjectAssignment(this);
        project.addTeamMember(this);
        lastModifiedAt = LocalDateTime.now();
    }
}
