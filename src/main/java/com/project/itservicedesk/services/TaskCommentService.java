package com.project.itservicedesk.services;

import com.project.itservicedesk.models.TaskComment;
import com.project.itservicedesk.repositories.TaskCommentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    public List<TaskComment> getAllCommentsByTaskId(Long id) {
        return taskCommentRepository.findAllCommentsByTaskId(id);
    }

    public TaskComment save(TaskComment comment) {
        if (comment.getId() == null) {
            comment.setCreatedDate(LocalDateTime.now());
        }
        return taskCommentRepository.save(comment);
    }

    public List<TaskComment> getAllComments() {
        return taskCommentRepository.findAll();
    }

    public TaskComment findCommentById(Long id) {
        return taskCommentRepository.findById(id).orElseThrow();
    }

    public void delete(TaskComment taskComment) {
        taskCommentRepository.delete(taskComment);
    }
}
