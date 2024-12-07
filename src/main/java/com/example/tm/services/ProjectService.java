package com.example.tm.services;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    //private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> getAllByUser(AppUser user) {
        return projectRepository.findAllByUsersContains(user);
    }

    public Project getById(UUID id) {
       return projectRepository.findById(id).orElseThrow();
    }

    public List<Project> getAllByIds(List<UUID> ids) {
        return projectRepository.findAllById(ids);
    }

    public boolean deleteById(UUID id) {
        getById(id);
        projectRepository.deleteById(id);
        return true;
    }

}
