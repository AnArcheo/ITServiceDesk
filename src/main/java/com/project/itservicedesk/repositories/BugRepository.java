package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Bug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
//    @Query(value = "SELECT * FROM bugs WHERE CONCAT(title, status, priority) ILIKE '%?1%'", nativeQuery = true)
//    Page<Bug> searchRepository(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM bugs WHERE CONCAT(title, description, status, priority, project_id, reporter_id, user_id) ILIKE (CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Bug> searchRepository(String keyword, Pageable pageable);

    Page<Bug> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM bugs WHERE reporter_id=?1 or user_id=?1", nativeQuery = true)
    List<Bug> findAllByAssignedToOrCreatedBy(Long id);

    List<Bug> findByTitle(String title);
}
