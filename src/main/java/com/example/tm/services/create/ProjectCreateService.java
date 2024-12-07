package com.example.tm.services.create;

import com.example.tm.dto.request.create.ProjectCreate;
import com.example.tm.dto.request.create.mappers.ProjectCreateMapper;
import com.example.tm.dto.request.update.ProjectUpdate;
import com.example.tm.dto.request.update.mappers.ProjectUpdateMapper;
import com.example.tm.entities.AppUser;
import com.example.tm.entities.Project;
import com.example.tm.repositories.ProjectRepository;
import com.example.tm.services.AppUserService;
import com.example.tm.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateService {

    private final ProjectRepository projectRepository;
    private final AppUserService appUserService;
    //private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectCreateMapper projectCreateMapper;
    private final ProjectUpdateMapper projectUpdateMapper;
    private final TaskService taskService;

    public ProjectCreateService(ProjectRepository projectRepository, AppUserService appUserService, ProjectCreateMapper projectCreateMapper, ProjectUpdateMapper projectUpdateMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.appUserService = appUserService;
        this.projectCreateMapper = projectCreateMapper;
        this.projectUpdateMapper = projectUpdateMapper;
        this.taskService = taskService;
    }

    @Transactional
    public Project createProject(ProjectCreate tasklist) {
        List<AppUser> userList = appUserService.getAllByIds(tasklist.userIds()); //user who created the list will be in this
        if (userList.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }
        Project tl = projectCreateMapper.toEntity(tasklist);
        tl.setUsers(userList);
        return projectRepository.save(tl);
    }

    @Transactional
    public Project updateProject(UUID listId, ProjectUpdate tasklist) {

        Project oldProject = projectRepository.findById(listId).orElseThrow();
        List<AppUser> oldUsers = oldProject.getUsers();

        List<Task> newTasks = taskService.getAllByIds(tasklist.taskIds()); //orphan removal is true, no need to manually remove old tasks
        List<AppUser> newUsers = appUserService.getAllByIds(tasklist.userIds());

        if (newUsers.isEmpty()) {
            //projectRepository.delete(oldProject); //if no list has no users, delete the list along with associated tasks
            throw new IllegalArgumentException("There must be at least one user of the list.");
        }

        Project newProject = projectUpdateMapper.toEntity(tasklist);
        newProject.setId(listId);
        newProject.setTasks(newTasks);
        newProject.setUsers(newUsers);

        handleUserUpdate(oldUsers,newUsers, oldProject);

        return projectRepository.save(newProject);
    }

    public void handleUserUpdate(List<AppUser> oldUsers, List<AppUser> newUsers, Project oldProject) {
        for(AppUser old : oldUsers) {
            if (!newUsers.contains(old)) {
                old.removeProject(oldProject);
            }
        }
    }
}
