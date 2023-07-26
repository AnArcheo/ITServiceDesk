package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.BugComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugCommentRepository extends JpaRepository<BugComment, Long> {
}
