package com.project.itservicedesk.models;

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

@Getter
@Setter
@Builder
@ToString(of = {"title", "project", "createdDate"})
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

    @Column(name="created_date", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable=false, updatable=false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable=false, updatable=false)
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Lob
    private Byte[] attachment;
}
