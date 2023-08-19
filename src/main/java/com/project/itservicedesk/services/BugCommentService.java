package com.project.itservicedesk.services;

import com.project.itservicedesk.models.BugComment;
import com.project.itservicedesk.models.TaskComment;
import com.project.itservicedesk.repositories.BugCommentRepository;
import com.project.itservicedesk.repositories.TaskCommentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BugCommentService {

    private final BugCommentRepository bugCommentRepository;

    public List<BugComment> getAllCommentsByBugId(Long id) {
        return bugCommentRepository.findAllCommentsByBugId(id);
    }

    public BugComment save(BugComment comment) {
        if (comment.getId() == null) {
            comment.setCreatedDate(LocalDateTime.now());
        }
        return bugCommentRepository.save(comment);
    }

    public List<BugComment> getAllComments() {
        return bugCommentRepository.findAll();
    }

    public BugComment findCommentById(Long id) {
        return bugCommentRepository.findById(id).orElseThrow();
    }

    public void delete(BugComment bugComment) {
        bugCommentRepository.delete(bugComment);
    }
}
