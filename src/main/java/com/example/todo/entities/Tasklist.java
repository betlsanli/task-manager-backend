package com.example.todo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Tasklist extends BaseEntity{

    @Column(nullable = false, length = 126)
    private String title;

    @Column(length = 512)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "belongsTo")
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //owner of the relationship
    @JoinTable(name = "user_tasklist",
            joinColumns = {
                    @JoinColumn(name = "tasklist_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id")
            }
    )
    //@JsonIgnore
    @Builder.Default
    private List<AppUser> users = new ArrayList<>();

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
