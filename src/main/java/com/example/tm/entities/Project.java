package com.example.tm.entities;

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
public class Project extends BaseEntity{

    @Column(nullable = false, length = 126)
    private String title;

    @Column(length = 512)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "belongsTo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    //@JsonIgnore
    @Builder.Default
    private List<AppUser> users = new ArrayList<>();


    @PrePersist
    public void onPersist(){
        for(AppUser user : users){
            user.addProject(this);
        }
    }
    @PreUpdate
    protected void onUpdate() {
        for(AppUser user : users){
            user.addProject(this);
        }
        for(Task task : tasks){
            task.setBelongsTo(this);
        }
    }

    public void addUser(AppUser user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }
    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    public void removeUser(AppUser user) {
        users.remove(user);
    }
}
