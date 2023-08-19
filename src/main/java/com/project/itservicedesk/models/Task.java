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
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(of = {"title"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    @NotEmpty(message = "Title cannot be empty!")
    @Size(min = 10, max = 255, message = "Title must have minimum 10 and maximum 255 characters long")
    private String title;

    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Column(name="priority")
    @Enumerated(value = EnumType.STRING)
    private PriorityStatus priority;

    @Column(name="created_date", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Due Date cannot be empty!")
    private Date dueDate;

    @Column(name="modified_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @JsonManagedReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @JsonManagedReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User assignee;

    @JsonManagedReference
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonBackReference
    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="task_id", referencedColumnName = "id")
    private Set<TaskAttachment> taskAttachments;

    @JsonBackReference
    @OneToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="task_id", referencedColumnName = "id")
    private List<TaskComment> taskComments;


}
