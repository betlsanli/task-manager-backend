package com.example.todo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tasklist> tasklists;

    public void addTasklist(Tasklist tasklist){
        if(this.tasklists == null){
            this.tasklists = new ArrayList<>();
        }
        if(!this.tasklists.contains(tasklist)){
            this.tasklists.add(tasklist);
        }
    }
}
