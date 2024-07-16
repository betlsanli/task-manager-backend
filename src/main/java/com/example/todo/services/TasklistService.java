package com.example.todo.services;

import com.example.todo.dto.AppUserDTO;
import com.example.todo.dto.TasklistDTO;
import com.example.todo.dto.mappers.AppUserDTOMapper;
import com.example.todo.dto.mappers.TasklistDTOMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TasklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TasklistService {

    private final TasklistRepository tasklistRepository;
    private final TasklistDTOMapper tasklistDTOMapper;
    private final AppUserDTOMapper appUserDTOMapper;

    @Autowired
    public TasklistService(TasklistRepository tasklistRepository, TasklistDTOMapper tasklistDTOMapper, AppUserDTOMapper appUserDTOMapper) {
        this.tasklistRepository = tasklistRepository;
        this.tasklistDTOMapper = tasklistDTOMapper;
        this.appUserDTOMapper = appUserDTOMapper;
    }

    public List<TasklistDTO> getAll() {
        return tasklistRepository.findAll().stream().map(tasklistDTOMapper::apply).collect(Collectors.toList());
    }

    public List<TasklistDTO> getAllByUser(AppUserDTO userDTO) {
        AppUser user = appUserDTOMapper.toEntity(userDTO);
        return tasklistRepository.findAllByUsersContains(user).stream().map(tasklistDTOMapper::apply).collect(Collectors.toList());
    }

    public Optional<TasklistDTO> getById(UUID id) {
        return tasklistRepository.findById(id).stream().map(tasklistDTOMapper::apply).findFirst();
    }

    public Tasklist save(Tasklist tasklist) {
        tasklist.setLastModifiedAt(LocalDateTime.now());
        return tasklistRepository.save(tasklist);
    }

    public void deleteById(UUID id) {
        tasklistRepository.deleteById(id);
    }
}
