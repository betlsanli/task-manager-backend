package com.example.todo.services;

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

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

    public Task save(Task newTask) {
        newTask.setLastModifiedAt(LocalDateTime.now());
        return taskRepository.save(newTask);
    }

    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }
}
