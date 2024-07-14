package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.AppUserRepository;
import com.example.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {


    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> getById(UUID id) {
        return taskRepository.findById(id);
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<Task> getAllByTasklist(Tasklist tasklist) {
        return taskRepository.findAllByBelongsTo(tasklist);
    }

    public List<Task> getAllByTasklists(List<Tasklist> tasklists) {
        return taskRepository.findAllByBelongsToIn(tasklists);
    }

    public List<Task> getAllByParentTask(Task parent) {
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
