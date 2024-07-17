package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final AppUserService appUserService;
    private final TasklistService tasklistService;
   // private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public TaskService(TaskRepository taskRepository, AppUserService appUserService, TasklistService tasklistService) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
        this.tasklistService = tasklistService;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getById(UUID id) {
        return taskRepository.findById(id).orElseThrow();
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<Task> getAllByTasklist(UUID listId) {
        Tasklist tasklist = tasklistService.getById(listId);
        return taskRepository.findAllByBelongsTo(tasklist);
    }

    public List<Task> getAllByUser(UUID userID) {
        AppUser user = appUserService.getById(userID);
        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
        return taskRepository.findAllByBelongsToIn(tasklists);
    }

    public List<Task> getAllByParentTask(Task parent) {
        return taskRepository.findAllByParentTask(parent);
    }

    public List<Task> getAllByIds(List<UUID> ids) {
        return taskRepository.findAllById(ids);
    }

    public void deleteById(UUID id) {
        getById(id);
        taskRepository.deleteById(id);
    }

}
