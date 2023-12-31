package com.project.itservicedesk.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(of = {"title", "description", "createdDate", "reporter", "project"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bugs")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    @NotEmpty(message = "Title cannot be empty!")
    @Size(min = 5, max = 50, message = "Title must have minimum 5 characters and maximum 30 characters long")
    private String title;

    @Column(name="description", nullable = false)
    @NotEmpty(message = "Issue description cannot be empty!")
    @Size(min = 10, max = 255, message = "Issue description must have minimum 10 characters and maximum 255 characters long")
    private String description;

    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private BugStatus status;

    @Column(name="priority")
    @Enumerated(value = EnumType.STRING)
    private PriorityStatus priority;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name="created_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Due Date cannot be empty!")
    private Date dueDate;

    @JsonBackReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "reporter_id", referencedColumnName = "id")
    private User reporter;

    @JsonBackReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User assignee;

    @Column(name="modified_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @JsonManagedReference
    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="bug_id", referencedColumnName = "id")
    private Set<BugAttachment> bugAttachments;

    @JsonManagedReference
    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="bug_id", referencedColumnName = "id")
    private Set<BugComment> bugComments;
}
