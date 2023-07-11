package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Long, Task> {

    @Query(value = "SELECT * FROM tasks WHERE CONCAT(title, status, priority) ILIKE %?1%", nativeQuery = true)
    Page<Task> searchRepository(String keyword, Pageable pageable);
//    Page<Task> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
//    Page<Task> findByStatusContainingIgnoreCase(String keyword, Pageable pageable);
//    Page<Task> findByPriorityContainingIgnoreCase(String keyword, Pageable pageable);
}
