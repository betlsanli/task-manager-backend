package com.example.tm.services;

import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final ProjectService projectService;
    //private static final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, ProjectService projectService) {
        this.appUserRepository = appUserRepository;
        this.projectService = projectService;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getById(UUID id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public List<AppUser> getAllByIds(List<UUID> ids) {
        return appUserRepository.findAllById(ids);
    }

    public List<AppUser> getAllByList(UUID listId) {
        Project project = projectService.getById(listId);
        return appUserRepository.findAllByProjectContains(project);
    }

    public boolean deleteById(UUID id) {
        getById(id);
        appUserRepository.deleteById(id);
        return true;
    }
}
