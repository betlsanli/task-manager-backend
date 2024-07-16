package com.example.todo.services;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.dto.request.create.mappers.TaskCreateMapper;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TasklistService tasklistService;
    private final TaskCreateMapper taskCreateMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TasklistService tasklistService, TaskCreateMapper taskCreateMapper) {
        this.taskRepository = taskRepository;
        this.tasklistService = tasklistService;
        this.taskCreateMapper = taskCreateMapper;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getById(UUID id) {
        try {
            return taskRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<Task> getAllByTasklist(Tasklist tasklist) {
        if(tasklist == null) {
            return null;
        }
        return taskRepository.findAllByBelongsTo(tasklist);
    }

    public List<Task> getAllByTasklists(List<Tasklist> tasklists) {
        if(tasklists == null || tasklists.isEmpty()) {
            return null;
        }
        return taskRepository.findAllByBelongsToIn(tasklists);
    }

    public List<Task> getAllByParentTask(Task parent) {
        if(parent == null) {
            return null;
        }
        return taskRepository.findAllByParentTask(parent);
    }

    public List<Task> getAllByIds(List<UUID> ids) {
        if(ids == null || ids.isEmpty()) {
            return null;
        }
        return taskRepository.findAllById(ids);
    }

    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }

    public Task createTask(TaskCreate taskCreate) {
        if(taskCreate == null) {
            return null;
        }
        Task parentTask = taskRepository.findById(taskCreate.parentId()).orElse(null);
        Tasklist tl = tasklistService.getById(taskCreate.listId());
        if(tl == null) {
            return null;
        }
        Task newTask = taskCreateMapper.toEntity(taskCreate, parentTask, tl);
        return taskRepository.save(newTask);
    }
}
