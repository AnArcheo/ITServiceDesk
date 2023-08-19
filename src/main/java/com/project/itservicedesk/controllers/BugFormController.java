package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.ProjectService;
import com.project.itservicedesk.services.BugAttachmentService;
import com.project.itservicedesk.services.BugService;
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
public class BugFormController {

    private BugService bugService;
    private ProjectService projectService;
    private UserService userService;
    private BugAttachmentService bugAttachmentService;

    @GetMapping("/bugs/createBug")
    public String displayCreateBugForm(Bug bug, Model model){

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        List<BugAttachment> attachments = bugAttachmentService.getAllAttachments().toList();
        model.addAttribute("attachments", attachments);
        return "newBugForm";
    }

    @PostMapping("/bugs/submitNewBug")
    public String createNewBug(@Valid @ModelAttribute("bug")Bug bug, BindingResult result,
                                Model model, Errors errors){

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);
        List<BugAttachment> attachments = bugAttachmentService.getAllAttachments().toList();
        model.addAttribute("attachments", attachments);

        if(result.hasErrors()){
            model.addAttribute("listOfUsers", listOfUsers);
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("attachments", attachments);
            return "newBugForm";
        }

        String username = getCurrentLoggedUsername();

        User reporter = userService.findUserByUsername(username).orElseThrow();

        bug.setReporter(reporter);

        Bug newBug = new Bug();
        newBug.setTitle(bug.getTitle());
        newBug.setDescription(bug.getDescription());
        newBug.setStatus(bug.getStatus());
        newBug.setPriority(bug.getPriority());
        newBug.setDueDate(bug.getDueDate());

        newBug.setReporter(reporter);
        newBug.setAssignee(bug.getAssignee());
        newBug.setProject(bug.getProject());

        bugService.save(bug);
        return "redirect:/bugs";
    }

    @PostMapping("/bugs/edit/{id}")
    public String editBug(@PathVariable("id") Long id, @Valid @ModelAttribute("bug")Bug bug,
                           Model model, BindingResult result, Errors errors){

        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);

        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);
        //TODO AD TO OTHER CONTROLLERS MODELS WHEN ERROR

        if(result.hasErrors()){
            model.addAttribute("listOfUsers", listOfUsers);
            model.addAttribute("listOfProjects", listOfProjects);
            return "editBugForm";
        }

        Bug bugToUpdate = bugService.findBugById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such Bug ID"));

        String username = getCurrentLoggedUsername();

        User reporter = userService.findUserByUsername(username).orElseThrow();
        bug.setReporter(reporter);

        bugToUpdate.setTitle(bug.getTitle());
        bugToUpdate.setDescription(bug.getDescription());
        bugToUpdate.setStatus(bug.getStatus());
        bugToUpdate.setPriority(bug.getPriority());
        bugToUpdate.setDueDate(bug.getDueDate());

        bugToUpdate.setReporter(reporter);
        bugToUpdate.setAssignee(bug.getAssignee());
        bugToUpdate.setProject(bug.getProject());
        bugService.save(bugToUpdate);

        return "redirect:/bugs";
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
