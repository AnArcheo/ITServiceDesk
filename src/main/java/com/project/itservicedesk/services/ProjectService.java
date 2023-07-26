package com.project.itservicedesk.services;


import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectsRepository;

    public List<Project> getAllProjects(){
        return projectsRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id){
        return projectsRepository.findById(id);
    }

    public Project save(Project project){
        return projectsRepository.save(project);
    }

    public void delete(Project project){
        projectsRepository.delete(project);
    }

    public void deleteById(Long id){
        projectsRepository.deleteById(id);
    }

}
