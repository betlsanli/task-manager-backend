package com.example.todo.services.create;

import com.example.todo.dto.request.create.TaskCreate;
import com.example.todo.dto.request.create.mappers.TaskCreateMapper;
import com.example.todo.dto.request.update.TaskUpdate;
import com.example.todo.dto.request.update.mappers.TaskUpdateMapper;
import com.example.todo.entities.Task;
import com.example.todo.entities.Tasklist;
import com.example.todo.repositories.TaskRepository;
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

    public TaskCreateService(TaskRepository taskRepository, TasklistService tasklistService, TaskCreateMapper taskCreateMapper, TaskUpdateMapper taskUpdateMapper) {
        this.taskRepository = taskRepository;
        this.tasklistService = tasklistService;
        this.taskCreateMapper = taskCreateMapper;
        this.taskUpdateMapper = taskUpdateMapper;
    }

    @Transactional
    public Task createTask(TaskCreate taskCreate) {
        Task parentTask = null;
        if(taskCreate.parentId() != null)
            parentTask = taskRepository.findById(taskCreate.parentId()).orElse(null);
        Tasklist tl = tasklistService.getById(taskCreate.listId());
        Task newTask = taskCreateMapper.toEntity(taskCreate, parentTask, tl);
        tl.addTask(newTask);
        if(parentTask != null) {
            parentTask.addSubTask(newTask);
        }
        return taskRepository.save(newTask);
    }

    @Transactional
    public Task updateTask(UUID taskId, TaskUpdate taskUpdate) {
        taskRepository.findById(taskId).orElseThrow();
        Task parentTask = null;
        if(taskUpdate.parentId() != null)
            parentTask = taskRepository.findById(taskUpdate.parentId()).orElse(null);
        Tasklist list = tasklistService.getById(taskUpdate.listId());
        Task newTask = taskUpdateMapper.toEntity(taskUpdate, taskId, parentTask,list);
        list.addTask(newTask);
        if(parentTask != null) {
            parentTask.addSubTask(newTask);
        }
        return taskRepository.save(newTask);
    }
}
