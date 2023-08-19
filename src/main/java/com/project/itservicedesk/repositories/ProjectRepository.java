package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects WHERE CONCAT(id, project_name) ILIKE (CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Project> searchRepository(String keyword, Pageable pageable);


    Page<Project> findAll(Pageable pageable);
    @Query(value = "SELECT p FROM Project p WHERE p.projectName =?1")
    public Project findProjectByName(String name);
}
