package com.example.todo.services;

import com.example.todo.dto.response.TaskResponseDTO;
import com.example.todo.dto.response.mappers.TaskResponseDTOMapper;
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
    private final TaskResponseDTOMapper taskResponseDTOMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, AppUserService appUserService, TasklistService tasklistService, TaskResponseDTOMapper taskResponseDTOMapper) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
        this.tasklistService = tasklistService;
        this.taskResponseDTOMapper = taskResponseDTOMapper;
    }

    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAllByParentTaskIsNull().stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public TaskResponseDTO getById(UUID id) {
        return taskRepository.findById(id).stream().map(taskResponseDTOMapper::toDTO).findFirst().orElseThrow();
    }

//    public List<Task> getAllByUser(AppUser user) {
//        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
//        return taskRepository.findAllByBelongsToIn(tasklists);
//    }

    public List<TaskResponseDTO> getAllByTasklist(UUID listId) {
        Tasklist tasklist = tasklistService.getById(listId);
        return taskRepository.findAllByBelongsToAndParentTaskIsNull(tasklist).stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public List<TaskResponseDTO> getAllByUser(UUID userID) {
        AppUser user = appUserService.getById(userID);
        List<Tasklist> tasklists = tasklistService.getAllByUser(user);
        return taskRepository.findAllByBelongsToInAndParentTaskIsNull(tasklists).stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public List<TaskResponseDTO> getAllByParentTask(Task parent) {
        return taskRepository.findAllByParentTask(parent).stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public List<Task> getAllByIds(List<UUID> ids) {
        return taskRepository.findAllById(ids);
    }

    public boolean deleteById(UUID id) {
        getById(id);
        taskRepository.deleteById(id);
        return true;
    }

}
