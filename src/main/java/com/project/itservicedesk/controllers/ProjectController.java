package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.*;
import com.project.itservicedesk.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private static final int[] PAGE_SIZES = {5, 10, 15, 25};

    private final ProjectService projectService;
    private final CompanyService companyService;

    @GetMapping("/projects")
    public String showAllProjectsWithPagingAndSorting(Model model,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort) {

        try {
            List<Project> projects = new ArrayList<Project>();
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(order));

            Page<Project> pageProjects;

            if (keyword == null) {
                pageProjects = projectService.getAll(pageable);
            } else {
                pageProjects = projectService.searchRepository(keyword, pageable);
                model.addAttribute("keyword", keyword);
                if (pageProjects.isEmpty()) {
                    model.addAttribute("message", "No Projects Found!!!");
                }
            }
            // model.addAttribute("projectList", projectService.getAllProjects());
            projects = pageProjects.getContent();


            model.addAttribute("pageName", "projects"); //for paging and sorting
            model.addAttribute("page", page);
            model.addAttribute("projectList", projects);
            model.addAttribute("currentPage", pageProjects.getNumber() + 1);
            model.addAttribute("totalItems", pageProjects.getTotalElements());
            model.addAttribute("totalPages", pageProjects.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizesList", PAGE_SIZES);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "projects";
    }


    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectService.delete(projectService.findProjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project does not exist")));
//        projectService.deleteById(id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findProjectById(id));
        List<Company> listOfCompanies = companyService.getAllCompanies();
        model.addAttribute("listOfCompanies_edit", listOfCompanies);
        //load list of projects
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        return "editProjectForm";
    }
}
