package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final TasklistService tasklistService;
    //private static final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, TasklistService tasklistService) {
        this.appUserRepository = appUserRepository;
        this.tasklistService = tasklistService;
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
        Tasklist tasklist = tasklistService.getById(listId);
        return appUserRepository.findAllByTasklistsContains(tasklist);
    }

    public boolean deleteById(UUID id) {
        getById(id);
        appUserRepository.deleteById(id);
        return true;
    }
}
