package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.TaskAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Long> {
    @Query(value = "SELECT * FROM task_attachments ta WHERE ta.task_id = ?1", nativeQuery = true)
    List<TaskAttachment> findAllByTaskId(Long id);
}
