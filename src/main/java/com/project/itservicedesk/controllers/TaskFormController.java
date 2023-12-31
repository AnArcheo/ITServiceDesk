package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.ProjectService;
import com.project.itservicedesk.services.TaskAttachmentService;
import com.project.itservicedesk.services.TaskService;
import com.project.itservicedesk.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class TaskFormController {

    private TaskService taskService;
    private ProjectService projectService;
    private UserService userService;
    private TaskAttachmentService taskAttachmentService;

    @GetMapping("/tasks/createTask")
    public String displayCreateTaskForm(Task task, Model model){
        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);
        List<TaskAttachment> attachments = taskAttachmentService.getAllAttachments().toList();
        model.addAttribute("attachments", attachments);
        return "newTaskForm";
    }

    @PostMapping("/tasks/submitNewTask")
    public String createNewTask(@Valid @ModelAttribute("task")Task task, BindingResult result,
                                Model model, Errors errors){

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);
        List<TaskAttachment> attachments = taskAttachmentService.getAllAttachments().toList();
        model.addAttribute("attachments", attachments);

        if(result.hasErrors()){
            model.addAttribute("listOfUsers", listOfUsers);
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("attachments", attachments);
            return "newTaskForm";
        }


        String username = getCurrentLoggedUsername();
        User creator = userService.findUserByUsername(username).orElseThrow();

        task.setCreator(creator);

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setStatus(task.getStatus());
        newTask.setPriority(task.getPriority());
        newTask.setDueDate(task.getDueDate());
        newTask.setCreator(task.getCreator());
        newTask.setAssignee(task.getAssignee());
        newTask.setProject(task.getProject());

        taskService.save(task);
        return "redirect:/tasks";
    }



    @PostMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, @Valid @ModelAttribute("task")Task task,
                           Model model, BindingResult result, Errors errors){

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);
        List<TaskAttachment> attachments = taskAttachmentService.getAllAttachments().toList();
        model.addAttribute("attachments", attachments);
        //TODO AD TO OTHER CONTROLLERS MODELS WHEN ERROR

        if(result.hasErrors()){
            model.addAttribute("listOfUsers", listOfUsers);
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("attachments", attachments);
            return "editTaskForm";
        }


        Task taskToUpdate = taskService.findTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such Task ID"));

        String username = getCurrentLoggedUsername();

        User creator = userService.findUserByUsername(username).orElseThrow();
        task.setCreator(creator);

        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setStatus(task.getStatus());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setCreator(task.getCreator());
        taskToUpdate.setAssignee(task.getAssignee());
        taskToUpdate.setProject(task.getProject());
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
