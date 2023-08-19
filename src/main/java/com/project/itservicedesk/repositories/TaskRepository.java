package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM tasks WHERE CONCAT(title, status, priority, creator_id, user_id, project_id) ILIKE (CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Task> searchRepository(String keyword, Pageable pageable);

    Page<Task> findAll(Pageable pageable);


    @Query(value = "SELECT * FROM tasks WHERE creator_id=?1 or user_id=?1", nativeQuery = true)
    List<Task> findAllByAssignedToOrCreatedBy(Long id);
//    Page<Task> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
//    Page<Task> findByStatusContainingIgnoreCase(String keyword, Pageable pageable);
//    Page<Task> findByPriorityContainingIgnoreCase(String keyword, Pageable pageable);
}
