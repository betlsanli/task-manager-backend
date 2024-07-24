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
        Tasklist tl = tasklistService.getById(taskCreate.listId());
        Task newTask = taskCreateMapper.toEntity(taskCreate, parentTask, tl);
        tl.addTask(newTask);
        if(parentTask != null) {
            parentTask.addSubTask(newTask);
        }
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

        AppUser oldAssignee = oldTask.getAssignee();
        AppUser newAssignee = null;
        if(taskUpdate.assigneeId() != null)
            newAssignee = appUserService.getById(taskUpdate.assigneeId());

        Tasklist oldList = oldTask.getBelongsTo();
        Tasklist newList = tasklistService.getById(taskUpdate.listId());

        Task newTask = taskUpdateMapper.toEntity(taskUpdate, taskId, newParent, newList, newAssignee);

        if(oldList.getId() != newList.getId()) {
            oldList.removeTask(oldTask);
            newList.addTask(newTask);
        }
        if(oldAssignee.getId() != newAssignee.getId()) {
            oldAssignee.removeAssignedTask(oldTask);
            newAssignee.addAssignedTask(newTask);
        }
        if(oldParent.getId() != newParent.getId()) {
            oldParent.removeSubTask(oldTask);
            if(newParent != null)
                newParent.addSubTask(newTask);
        }
        taskRepository.save(newTask);
        return taskResponseDTOMapper.toDTO(newTask);
    }
}
