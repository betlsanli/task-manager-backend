package com.example.todo.services;

import com.example.todo.dto.request.create.TasklistCreate;
import com.example.todo.dto.request.create.mappers.TasklistCreateMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TasklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TasklistService {

    private final TasklistRepository tasklistRepository;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(TasklistService.class);
    private final TasklistCreateMapper tasklistCreateMapper;

    @Autowired
    public TasklistService(TasklistRepository tasklistRepository, AppUserService appUserService, TasklistCreateMapper tasklistCreateMapper) {
        this.tasklistRepository = tasklistRepository;
        this.appUserService = appUserService;
        this.tasklistCreateMapper = tasklistCreateMapper;
    }

    public List<Tasklist> getAll() {
        return tasklistRepository.findAll();
    }

    public List<Tasklist> getAllByUser(AppUser user) {
        if (user == null) {
            return null;
        }
        return tasklistRepository.findAllByUsersContains(user);
    }

    public Tasklist getById(UUID id) {
        try {
            return tasklistRepository.findById(id).orElseThrow();

        }catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void deleteById(UUID id) {
        tasklistRepository.deleteById(id);
    }

    public Tasklist createTasklist(TasklistCreate tasklist) {
        if (tasklist == null) {
            return null;
        }
        List<AppUser> userList = appUserService.getAllByIds(tasklist.userIds());
        if (userList == null ||userList.isEmpty()) {
            return null;
        }
        Tasklist tl = tasklistCreateMapper.toEntity(tasklist, userList);
        return tasklistRepository.save(tl);
    }
}
