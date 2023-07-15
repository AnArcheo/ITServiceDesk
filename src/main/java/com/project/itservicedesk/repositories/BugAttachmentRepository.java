package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.BugAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugAttachmentRepository extends JpaRepository<BugAttachment, Long> {
}
