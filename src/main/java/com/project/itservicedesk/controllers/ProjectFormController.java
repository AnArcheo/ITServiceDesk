package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Company;
import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.services.CompanyService;
import com.project.itservicedesk.services.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProjectFormController {


    private ProjectService projectService;
    private CompanyService companyService;


    @GetMapping("/projects/createProject")
    public String displayCreateProjectForm(@ModelAttribute("project") Project project, Model model){

        List<Company> listOfCompanies = companyService.getAllCompanies();
        model.addAttribute("listOfCompanies", listOfCompanies);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        return "newProjectForm";
    }

    @PostMapping("/projects/submitNewProject")
    public String createNewProject(@Valid @ModelAttribute("project")Project project, BindingResult result,
                                Model model, Errors errors){
        List<Company> listOfCompanies = companyService.getAllCompanies();
        model.addAttribute("listOfCompanies", listOfCompanies);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);


        if(result.hasErrors()){
            model.addAttribute("listOfCompanies", listOfCompanies);
            model.addAttribute("listOfProjects", listOfProjects);
            return "newProjectForm";
        }

        Project newProject = new Project();

        newProject.setProjectName(project.getProjectName());
        newProject.setCompany(project.getCompany());

        projectService.save(project);

        return "redirect:/projects";
    }



    @PostMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") Long id, @Valid @ModelAttribute("project") Project project,
                           Model model, BindingResult result, Errors errors){

        List<Company> listOfCompanies = companyService.getAllCompanies();
        model.addAttribute("listOfCompanies_edit", listOfCompanies);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        if(result.hasErrors()){
            model.addAttribute("listOfCompanies", listOfCompanies);
            model.addAttribute("listOfProjects", listOfProjects);
            return "editProjectForm";
        }

        Project projectToUpdate = projectService.findProjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such Project ID"));

        projectToUpdate.setProjectName(project.getProjectName());
        projectToUpdate.setCompany(project.getCompany());

        model.addAttribute("project", projectToUpdate);
        projectService.save(projectToUpdate);

        return "redirect:/projects";
    }

}
