package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
