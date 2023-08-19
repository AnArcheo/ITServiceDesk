package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.BugComment;
import com.project.itservicedesk.models.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugCommentRepository extends JpaRepository<BugComment, Long> {

    @Query(value = "SELECT * FROM bug_comments bc WHERE bc.bug_id = ?1", nativeQuery = true)
    List<BugComment> findAllCommentsByBugId(Long id);
}
