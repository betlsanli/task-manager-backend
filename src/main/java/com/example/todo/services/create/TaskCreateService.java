package com.example.todo.services.create;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.dto.request.create.mappers.TaskCreateMapper;
import com.example.todo.dto.request.update.TaskUpdate;
import com.example.todo.dto.request.update.mappers.TaskUpdateMapper;
import com.example.todo.dto.response.TaskResponseDTO;
import com.example.todo.dto.response.mappers.TaskResponseDTOMapper;
import com.example.todo.entities.AppUser;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TaskRepository;
import com.example.todo.services.AppUserService;
import com.example.todo.services.TasklistService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskCreateService {
    private final TaskRepository taskRepository;
   // private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TasklistService tasklistService;
    private final TaskCreateMapper taskCreateMapper;
    private final TaskUpdateMapper taskUpdateMapper;
    private final TaskResponseDTOMapper taskResponseDTOMapper;
    private final AppUserService appUserService;

    public TaskCreateService(TaskRepository taskRepository, TasklistService tasklistService, TaskCreateMapper taskCreateMapper, TaskUpdateMapper taskUpdateMapper, TaskResponseDTOMapper taskResponseDTOMapper, AppUserService appUserService) {
        this.taskRepository = taskRepository;
        this.tasklistService = tasklistService;
        this.taskCreateMapper = taskCreateMapper;
        this.taskUpdateMapper = taskUpdateMapper;
        this.taskResponseDTOMapper = taskResponseDTOMapper;
        this.appUserService = appUserService;
    }

    @Transactional
    public TaskResponseDTO createTask(TaskCreate taskCreate) {

        Task parentTask = null;
        if(taskCreate.parentId() != null)
            parentTask = taskRepository.findById(taskCreate.parentId()).orElse(null);

        List<AppUser> assignees;
        if(!taskCreate.assignees().isEmpty())
            assignees = appUserService.getAllByIds(taskCreate.assignees());
        else
            assignees = new ArrayList<>();

        Tasklist tl = tasklistService.getById(taskCreate.listId());

        Task newTask = taskCreateMapper.toEntity(taskCreate);
        newTask.setAssignees(assignees);
        newTask.setParentTask(parentTask);
        newTask.setBelongsTo(tl);

        taskRepository.save(newTask);
        return taskResponseDTOMapper.toDTO(newTask);
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID taskId, TaskUpdate taskUpdate) {
        Task oldTask = taskRepository.findById(taskId).orElseThrow();

        Task oldParent = oldTask.getParentTask();
        Task newParent = null;
        if(taskUpdate.parentId() != null)
            newParent = taskRepository.findById(taskUpdate.parentId()).orElse(null);

        List<AppUser> oldAssignees = oldTask.getAssignees();
        List<AppUser> newAssignees = taskUpdate.assignees();

        handleAssigneeUpdate(oldAssignees,newAssignees,oldTask);
        handleParentUpdate(oldParent,newParent,oldTask);

        Task newTask = taskUpdateMapper.toEntity(taskUpdate);
        newTask.setId(taskId);
        newTask.setParentTask(newParent);
        newTask.setBelongsTo(oldTask.getBelongsTo());

        taskRepository.save(newTask);
        return taskResponseDTOMapper.toDTO(newTask);
    }

    public void handleParentUpdate(Task oldParent, Task newParent, Task oldTask) {
        if(oldParent == null)
            return;
        else if(newParent == null){
            oldParent.removeSubTask(oldTask);
            return;
        }
        else if(oldParent.getId() != newParent.getId()) {
            oldParent.removeSubTask(oldTask);
        }
    }

    public void handleAssigneeUpdate(List<AppUser> oldAssignees, List<AppUser> newAssignees, Task oldTask) {
        for(AppUser oldAssignee : oldAssignees) {
            if(!newAssignees.contains(oldAssignee)) {
                oldAssignee.removeAssignedTask(oldTask);
            }
        }
    }
}
