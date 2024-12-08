package com.example.tm.repositories;

import com.example.tm.entities.ProjectAssignment;
import com.example.tm.entities.UserProjectRoleID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, UserProjectRoleID> {
    //List<ProjectAssignment> findByUserProjectRoleID(UserProjectRoleID userProjectRoleID);
    List<ProjectAssignment> findAllByProject_Id(UUID projectId);
    List<ProjectAssignment> findAllByUser_Id(UUID userId);
    List<ProjectAssignment> findAllByProject_IdAndUser_Id(UUID projectId, UUID userId);
 /*   List<ProjectAssignment> findAllByUser_IdAndRole_Id(UUID userId, int RoleId);
    List<ProjectAssignment> findAllByProject_IdAndRole_Id(UUID projectId, int RoleId);
    List<ProjectAssignment> findAllByProject_IdAndRole_IdAndUser_Id(UUID projectId, int RoleId, UUID userId);*/
}
