package com.project.itservicedesk.services;


import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id){
        return projectRepository.findById(id);
    }

    public Project save(Project project){
        return projectRepository.save(project);
    }

    public void delete(Project project){
        projectRepository.delete(project);
    }

    public void deleteById(Long id){
        projectRepository.deleteById(id);
    }

    public Page<Project> searchRepository(String keyword, Pageable pageable) {
        return projectRepository.searchRepository(keyword, pageable);
    }


    /**
     * Methods to return Pagination
     */
    public Page<Project> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Project findProjectByProjectName(String projectName) {
        return projectRepository.findProjectByName(projectName);
    }
}
