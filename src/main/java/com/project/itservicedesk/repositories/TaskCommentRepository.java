package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    @Query(value = "SELECT * FROM task_comments tc WHERE tc.task_id = ?1", nativeQuery = true)
    List<TaskComment> findAllCommentsByTaskId(Long id);
}
