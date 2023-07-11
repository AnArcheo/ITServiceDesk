package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
//    @Query(value = "SELECT * FROM bugs WHERE CONCAT(title, status, priority) ILIKE %?1%", nativeQuery = true)
//    Page<Bug> searchRepository(String keyword, Pageable pageable);
}
