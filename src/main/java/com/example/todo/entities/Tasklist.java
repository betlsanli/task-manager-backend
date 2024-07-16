package com.example.todo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany(mappedBy = "tasklists")
    private List<AppUser> users;
}
