package com.example.todo.services.create;

import com.example.todo.dto.request.create.TasklistCreate;
import com.example.todo.dto.request.create.mappers.TasklistCreateMapper;
import com.example.todo.dto.request.update.TasklistUpdate;
import com.example.todo.dto.request.update.mappers.TasklistUpdateMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TasklistRepository;
import com.example.todo.services.AppUserService;
import com.example.todo.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TasklistCreateService {

    private final TasklistRepository tasklistRepository;
    private final AppUserService appUserService;
    //private static final Logger log = LoggerFactory.getLogger(TasklistService.class);
    private final TasklistCreateMapper tasklistCreateMapper;
    private final TasklistUpdateMapper tasklistUpdateMapper;
    private final TaskService taskService;

    public TasklistCreateService(TasklistRepository tasklistRepository, AppUserService appUserService, TasklistCreateMapper tasklistCreateMapper, TasklistUpdateMapper tasklistUpdateMapper, TaskService taskService) {
        this.tasklistRepository = tasklistRepository;
        this.appUserService = appUserService;
        this.tasklistCreateMapper = tasklistCreateMapper;
        this.tasklistUpdateMapper = tasklistUpdateMapper;
        this.taskService = taskService;
    }

    @Transactional
    public Tasklist createTasklist(TasklistCreate tasklist) {
        List<AppUser> userList = appUserService.getAllByIds(tasklist.userIds());
        if (userList.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }
        Tasklist tl = tasklistCreateMapper.toEntity(tasklist, userList);
        for (AppUser user : userList) {
            user.addTasklist(tl);
        }
        return tasklistRepository.save(tl);
    }

    @Transactional
    public Tasklist updateTasklist(UUID listId, TasklistUpdate tasklist) {

        tasklistRepository.findById(listId).orElseThrow();
        List<Task> tasks = taskService.getAllByIds(tasklist.taskIds());
        List<AppUser> userList = appUserService.getAllByIds(tasklist.userIds());
        if (userList.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }
        Tasklist newTasklist = tasklistUpdateMapper.toEntity(tasklist,listId,tasks,userList);
        for (AppUser user : userList) {
            user.addTasklist(newTasklist);
        }
        return tasklistRepository.save(newTasklist);
    }
}
