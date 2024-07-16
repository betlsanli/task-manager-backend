package com.example.todo.services;

import com.example.todo.controllers.TasklistController;
import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.TasklistDTO;
import com.example.todo.dto.mappers.TaskDTOMapper;
import com.example.todo.dto.mappers.TasklistDTOMapper;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskDTOMapper taskDTOMapper;
    private final TasklistDTOMapper tasklistDTOMapper;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskDTOMapper taskDTOMapper, TasklistDTOMapper tasklistDTOMapper) {
        this.taskRepository = taskRepository;
        this.taskDTOMapper = taskDTOMapper;
        this.tasklistDTOMapper = tasklistDTOMapper;
    }

    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public TaskDTO getById(UUID id) {
        try {
            return taskRepository.findById(id).stream().map(taskDTOMapper::apply).findFirst().orElseThrow();
        }catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return null;
        }
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<TaskDTO> getAllByTasklist(TasklistDTO tasklistDTO) {
        if(tasklistDTO == null) {
            return null;
        }
        Tasklist tasklist = tasklistDTOMapper.toEntity(tasklistDTO);
        return taskRepository.findAllByBelongsTo(tasklist).stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public List<TaskDTO> getAllByTasklists(List<TasklistDTO> tasklistsDTO) {
        if(tasklistsDTO == null || tasklistsDTO.isEmpty()) {
            return null;
        }
        List<Tasklist> tasklists = tasklistsDTO.stream().map(tasklistDTOMapper::toEntity).collect(Collectors.toList());
        return taskRepository.findAllByBelongsToIn(tasklists).stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public List<TaskDTO> getAllByParentTask(Task parent) {
        if(parent == null) {
            return null;
        }
        return taskRepository.findAllByParentTask(parent).stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public Task save(Task newTask) {
        newTask.setLastModifiedAt(LocalDateTime.now());
        return taskRepository.save(newTask);
    }

    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }
}
