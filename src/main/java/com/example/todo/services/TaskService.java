package com.example.todo.services;

import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.TasklistDTO;
import com.example.todo.dto.mappers.TaskDTOMapper;
import com.example.todo.dto.mappers.TasklistDTOMapper;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskDTOMapper taskDTOMapper;
    private final TasklistDTOMapper tasklistDTOMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskDTOMapper taskDTOMapper, TasklistDTOMapper tasklistDTOMapper) {
        this.taskRepository = taskRepository;
        this.taskDTOMapper = taskDTOMapper;
        this.tasklistDTOMapper = tasklistDTOMapper;
    }

    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public Optional<TaskDTO> getById(UUID id) {
        return taskRepository.findById(id).stream().map(taskDTOMapper::apply).findFirst();
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<TaskDTO> getAllByTasklist(TasklistDTO tasklistDTO) {
        Tasklist tasklist = tasklistDTOMapper.toEntity(tasklistDTO);
        return taskRepository.findAllByBelongsTo(tasklist).stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public List<TaskDTO> getAllByTasklists(List<TasklistDTO> tasklistsDTO) {
        List<Tasklist> tasklists = tasklistsDTO.stream().map(tasklistDTOMapper::toEntity).collect(Collectors.toList());
        return taskRepository.findAllByBelongsToIn(tasklists).stream().map(taskDTOMapper::apply).collect(Collectors.toList());
    }

    public List<TaskDTO> getAllByParentTask(Task parent) {
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
