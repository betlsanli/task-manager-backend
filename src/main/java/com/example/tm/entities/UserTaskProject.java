package com.example.tm.entities;

import com.example.tm.enums.Role.Role;
import com.example.tm.enums.Role.RoleConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class UserTaskProject extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = true)
    private Role role;

    @PrePersist
    private void onPersist(){
        user.addAssignment(this);
        project.addAssignment(this);
        task.addAssignment(this);
    }

    @PreUpdate
    private void onUpdate() { //do not forget to remove assignment from old user and old project in the service
        this.setLastModifiedAt(LocalDateTime.now());
    }

}
