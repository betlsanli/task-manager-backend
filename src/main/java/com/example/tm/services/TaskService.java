package com.example.tm.services;

import com.example.tm.dto.response.TaskResponseDTO;
import com.example.tm.dto.response.mappers.TaskResponseDTOMapper;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.entities.Task;
import com.example.tm.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final AppUserService appUserService;
    private final ProjectService projectService;
   // private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskResponseDTOMapper taskResponseDTOMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, AppUserService appUserService, ProjectService projectService, TaskResponseDTOMapper taskResponseDTOMapper) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
        this.projectService = projectService;
        this.taskResponseDTOMapper = taskResponseDTOMapper;
    }

    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAllByParentTaskIsNull().stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public TaskResponseDTO getById(UUID id) {
        return taskRepository.findById(id).stream().map(taskResponseDTOMapper::toDTO).findFirst().orElseThrow();
    }

    public List<TaskResponseDTO> getAllByTasklist(UUID listId) {
        Project project = projectService.getById(listId);
        return taskRepository.findAllByBelongsToAndParentTaskIsNull(project).stream().map(taskResponseDTOMapper::toDTO).toList();
    }

    public List<TaskResponseDTO> getAllByUser(UUID userID) {
        AppUser user = appUserService.getById(userID);
        List<Project> projects = projectService.getAllByUser(user);
        return taskRepository.findAllByBelongsToInAndParentTaskIsNull(projects).stream().map(taskResponseDTOMapper::toDTO).toList();
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
