package com.project.itservicedesk.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(of = "email")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "username", unique = true, length = 30)
//    @Formula(value = "SUBSTRING(email, 0, CHARINDEX('@', email))")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "email", nullable = false, unique = true, length = 45)
    @Email(message = "Please provide a valid e-mail")
    private String email;

    @NotEmpty(message = "First Name cannot be empty")
    @Column(name = "first_name", nullable = false, length = 20)
    @Size(max = 20, message = "First Name must not exceed maximum of 20 characters long")
    private String firstname;

    @NotEmpty(message = "Last Name cannot be empty")
    @Column(name = "last_name", nullable = false, length = 20)
    @Size(max = 20, message = "Last Name must not exceed maximum of 20 characters long")
    private String lastname;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "user_token", length = 256)
    private String userToken;

    @Lob
    @Column(name = "profile_photo")
    private Byte[] profilePhoto;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //or CascadeType.REFRESH
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> projects;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assignee")
    private Set<Task> assignedTasks;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    private Set<Task> createdTasks;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assignee")
    private Set<Bug> assignedBugs;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reporter")
    private Set<Bug> reportedBugs;

    public void addRole(Role role) {
        this.roles.add(role);
    }


}
