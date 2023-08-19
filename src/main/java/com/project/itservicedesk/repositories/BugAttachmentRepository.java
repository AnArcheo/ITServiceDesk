package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.models.TaskAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugAttachmentRepository extends JpaRepository<BugAttachment, Long> {
    @Query(value = "SELECT * FROM bug_attachments ba WHERE ba.bug_id = ?1", nativeQuery = true)
    List<BugAttachment> findAllByBugId(Long id);
}
