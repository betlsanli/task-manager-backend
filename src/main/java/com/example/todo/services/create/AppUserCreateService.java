package com.example.todo.services.create;

import com.example.todo.dto.request.create.AppUserCreate;
import com.example.todo.dto.request.create.mappers.AppUserCreateMapper;
import com.example.todo.dto.request.update.AppUserUpdate;
import com.example.todo.dto.request.update.mappers.AppUserUpdateMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.AppUserRepository;
import com.example.todo.services.TasklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppUserCreateService {

    private final AppUserRepository appUserRepository;
    private static final Logger log = LoggerFactory.getLogger(AppUserCreateService.class);
    private final AppUserCreateMapper appUserCreateMapper;
    private final AppUserUpdateMapper appUserUpdateMapper;
    private final TasklistService tasklistService;

    public AppUserCreateService(AppUserRepository appUserRepository, AppUserCreateMapper appUserCreateMapper, AppUserUpdateMapper appUserUpdateMapper, TasklistService tasklistService) {
        this.appUserRepository = appUserRepository;
        this.appUserCreateMapper = appUserCreateMapper;
        this.appUserUpdateMapper = appUserUpdateMapper;
        this.tasklistService = tasklistService;
    }

    public AppUser createUser(AppUserCreate newUser) {
        AppUser toSave = appUserCreateMapper.toEntity(newUser);
        return appUserRepository.save(toSave);
    }

    public AppUser updateUser(UUID userId, AppUserUpdate updateUserRequest) {
        appUserRepository.findById(userId).orElseThrow();
        List<Tasklist> tasklists = tasklistService.getAllByIds(updateUserRequest.listIds());
        AppUser toSave = appUserUpdateMapper.toEntity(updateUserRequest,userId,tasklists);
        return appUserRepository.save(toSave);
    }
}
