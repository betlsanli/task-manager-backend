package com.example.tm.entities;

import com.example.tm.enums.Role.Role;
import com.example.tm.enums.Role.RoleConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserProjectRoleID implements Serializable {

    @Column(nullable = false, updatable = false)
    private UUID userId;
    @Column(nullable = false, updatable = false)
    private UUID projectId;
    @Column(nullable = false, updatable = false)
    private Role role;

    // Default constructor
    public UserProjectRoleID() {}

    // Constructor
    public UserProjectRoleID(UUID userId, UUID projectId, String role) {
        this.userId = userId;
        this.projectId = projectId;
        this.role = Role.valueOf(role);
    }

    // Getters and setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProjectRoleID that = (UserProjectRoleID) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(projectId, that.projectId) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId, role);
    }
}
