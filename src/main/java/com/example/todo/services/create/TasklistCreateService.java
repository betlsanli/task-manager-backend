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
        List<AppUser> userList = appUserService.getAllByIds(tasklist.userIds()); //user who created the list will be in this
        if (userList.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }
        Tasklist tl = tasklistCreateMapper.toEntity(tasklist);
        tl.setUsers(userList);
        return tasklistRepository.save(tl);
    }

    @Transactional
    public Tasklist updateTasklist(UUID listId, TasklistUpdate tasklist) {

        Tasklist oldTasklist = tasklistRepository.findById(listId).orElseThrow();
        List<AppUser> oldUsers = oldTasklist.getUsers();

        List<Task> newTasks = taskService.getAllByIds(tasklist.taskIds()); //orphan removal is true, no need to manually remove old tasks
        List<AppUser> newUsers = appUserService.getAllByIds(tasklist.userIds());

        if (newUsers.isEmpty()) {
            //tasklistRepository.delete(oldTasklist); //if no list has no users, delete the list along with associated tasks
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }

        Tasklist newTasklist = tasklistUpdateMapper.toEntity(tasklist);
        newTasklist.setId(listId);
        newTasklist.setTasks(newTasks);
        newTasklist.setUsers(newUsers);

        handleUserUpdate(oldUsers,newUsers,oldTasklist);

        return tasklistRepository.save(newTasklist);
    }

    public void handleUserUpdate(List<AppUser> oldUsers, List<AppUser> newUsers, Tasklist oldTasklist) {
        for(AppUser old : oldUsers) {
            if (!newUsers.contains(old)) {
                old.removeTasklist(oldTasklist);
            }
        }
    }
}
