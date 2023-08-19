package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.*;
import com.project.itservicedesk.services.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private static final int[] PAGE_SIZES = {5, 10, 15, 25};

    private final TaskService taskService;
    private final TaskAttachmentService taskAttachmentService;
    private final TaskCommentService taskCommentService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/tasks")
    public String showAllTasksWithPagingAndSorting(Model model,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort) {

        try {
            List<Task> tasks = new ArrayList<Task>();
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(order));

            Page<Task> pageTasks;

            if (keyword == null) {
                pageTasks = taskService.getAll(pageable);
            } else {
                pageTasks = taskService.searchRepository(keyword, pageable);
                model.addAttribute("keyword", keyword);
                if (pageTasks.isEmpty()) {
                    model.addAttribute("message", "No Tasks Found!!!");
                }
            }
            // model.addAttribute("taskList", taskService.getAllTasks());
            tasks = pageTasks.getContent();

            List<TaskAttachment> attachments = taskAttachmentService.getAllAttachments().toList();
            List<TaskComment> comments = taskCommentService.getAllComments();

            model.addAttribute("pageName", "tasks"); //for paging and sorting
            model.addAttribute("page", page);
            model.addAttribute("taskList", tasks);
            model.addAttribute("attachments", attachments);
            model.addAttribute("comments", comments);
            model.addAttribute("currentPage", pageTasks.getNumber() + 1);
            model.addAttribute("totalItems", pageTasks.getTotalElements());
            model.addAttribute("totalPages", pageTasks.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizesList", PAGE_SIZES);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "tasks";
    }

//    @PreAuthorize("hasAnyAuthority({'ADMIN', 'MANAGER'})")
    @RolesAllowed({"ADMIN" ,"MANAGER"})
    @GetMapping(value = "/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.delete(taskService.findTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist")));

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findTaskById(id));

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        return "editTaskForm";
    }


}
