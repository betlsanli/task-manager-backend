package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TasklistService {

    private final TasklistRepository tasklistRepository;

    @Autowired
    public TasklistService(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    public List<Tasklist> getAll() {
        return tasklistRepository.findAll();
    }

    public List<Tasklist> getAllByUser(AppUser user) {
        return tasklistRepository.findAllByUsersContains(user);
    }

    public Optional<Tasklist> getById(UUID id) {
        return tasklistRepository.findById(id);
    }

    public Tasklist save(Tasklist tasklist) {
        tasklist.setLastModifiedAt(LocalDateTime.now());
        return tasklistRepository.save(tasklist);
    }

    public void deleteById(UUID id) {
        tasklistRepository.deleteById(id);
    }
}
