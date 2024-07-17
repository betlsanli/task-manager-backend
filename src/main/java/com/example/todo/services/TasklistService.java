package com.example.todo.services;

import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TasklistService {

    private final TasklistRepository tasklistRepository;
    //private static final Logger log = LoggerFactory.getLogger(TasklistService.class);

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

    public Tasklist getById(UUID id) {
       return tasklistRepository.findById(id).orElseThrow();
    }

    public List<Tasklist> getAllByIds(List<UUID> ids) {
        return tasklistRepository.findAllById(ids);
    }

    public boolean deleteById(UUID id) {
        getById(id);
        tasklistRepository.deleteById(id);
        return true;
    }

}
