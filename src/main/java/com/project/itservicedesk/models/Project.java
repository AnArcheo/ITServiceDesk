package com.project.itservicedesk.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false, unique = true)
    private String projectName;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "projects")
    private Set<User> users;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    private List<Task> projectTasks;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    private List<Bug> projectBugs;


    @Override
    public String toString() {
        return projectName;
    }
}
