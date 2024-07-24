package com.example.todo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class AppUser extends  BaseEntity{

    @Column(unique=true, nullable = false,length = 320)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 128)
    private String firstName;

    @Column(nullable = false, length = 128)
    private String lastName;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Builder.Default
    private List<Tasklist> tasklists = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "assignees", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Task> assignedTasks = new ArrayList<>();

    @PreUpdate
    public void onUpdate(){
        for (Tasklist tasklist : tasklists) {
            tasklist.addUser(this);
        }
    }

    public void addTasklist(Tasklist tasklist){
        if(!this.tasklists.contains(tasklist)){
            this.tasklists.add(tasklist);
        }
    }

    public void removeTasklist(Tasklist tasklist){
        if(this.tasklists.contains(tasklist)){
            this.tasklists.remove(tasklist);
        }
    }
    public void addAssignedTask(Task task){
        if(!this.assignedTasks.contains(task)){
            this.assignedTasks.add(task);
        }
    }
    public void removeAssignedTask(Task task){
        if(this.assignedTasks.contains(task)){
            this.assignedTasks.remove(task);
        }
    }
}
