package com.project.itservicedesk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Formula;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(of = "email")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "username", length = 30)
    @Formula(value = "SUBSTRING(email, 0, CHARINDEX('@', email))")
    private String username;

    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    @Email
    private String email;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstname;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastname;

    @Column(name = "gender", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private String gender;

    @Column(name = "active", nullable = false)
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> projects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo")
    private Set<Bug> assignedBugs = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportedBy")
    private Set<Bug> reportedBugs = new HashSet<>();
}
