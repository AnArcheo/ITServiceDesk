package com.project.itservicedesk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString(of = {"author", "commentContent"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="task_comments")
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_date", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(name="comment_content", nullable = false)
    @NotEmpty(message = "Comment cannot be empty!")
    @Size(min = 2, max = 255, message = "Comment must have minimum 2 and maximum 255 characters long")
    private String commentContent;
}
