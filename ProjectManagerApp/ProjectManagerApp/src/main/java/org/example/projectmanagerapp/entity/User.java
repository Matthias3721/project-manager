package org.example.projectmanagerapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String username;

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<Project>projects = new ArrayList<>();
}