package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.ProjectService;
import com.project.itservicedesk.services.TaskService;
import com.project.itservicedesk.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskFormController {

    private TaskService taskService;
    private ProjectService projectService;
    private UserService userService;

    @GetMapping("/createTask")
    public String displayCreateTaskForm(Task task, Model model){
        //load list of users before opening new form
        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        //load list of projects
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        return "newTaskForm";
    }

    @PostMapping("/submitNewTask")
    public String createNewTask(@Valid @ModelAttribute("task")Task task, BindingResult result,
                                Model model, Errors errors){
//load list of users before opening new form
        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        //load list of projects
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        if(result.hasErrors()){
            return "newTaskForm";
        }

        String username = getCurrentLoggedUsername();

        listOfProjects.stream().findFirst();
        task.setCreatedBy(username);

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setStatus(task.getStatus());
        newTask.setPriority(task.getPriority());
        newTask.setDueDate(task.getDueDate());
        newTask.setCreatedBy(task.getCreatedBy());
        newTask.setAssignedTo(task.getAssignedTo());
        newTask.setProjectName(task.getProjectName());

//        userService.save(newUser);
//        projectService.save(newProject);

        taskService.save(task);
        return "redirect:/tasks";
    }



    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, @Valid Task task, BindingResult result, Errors errors){
        if(result.hasErrors()){
            return "editTaskForm";
        }
        Task taskToUpdate = taskService.findTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such Task ID"));
        taskToUpdate.setTitle(task.getTitle());
//        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setStatus(task.getStatus());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setCreatedBy(task.getCreatedBy());
        taskToUpdate.setAssignedTo(task.getAssignedTo());
        taskToUpdate.setProjectName(task.getProjectName());

        taskService.save(taskToUpdate);
        return "redirect:/tasks";
    }

    private static String getCurrentLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username ="";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
