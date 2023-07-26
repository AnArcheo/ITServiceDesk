package com.project.itservicedesk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @NotEmpty
    @Column(name = "username", unique = true, length = 30)
//    @Formula(value = "SUBSTRING(email, 0, CHARINDEX('@', email))")
    private String username;

    @Column(name = "password", nullable = false, length = 256)
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
    private Gender gender;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Lob
    @Column(name = "profile_photo")
    private Byte[] profilePhoto;


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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "assignee")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "creator")
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "assignee")
    private Set<Bug> assignedBugs = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reporter")
    private Set<Bug> reportedBugs = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
