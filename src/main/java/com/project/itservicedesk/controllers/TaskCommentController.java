package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.ResourceNotFoundException;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.TaskComment;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.TaskCommentService;
import com.project.itservicedesk.services.TaskService;
import com.project.itservicedesk.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskService taskService;
    private final TaskCommentService taskCommentService;
    private final UserService userService;

    //TODO: ad pagination and sorting plus add this to repository
    // show comments from Task page
    @GetMapping("/tasks/{id}/comments")
    public String showComments(@PathVariable("id") Long id, Model model) {

        Task task = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

        List<TaskComment> comments = taskCommentService.getAllCommentsByTaskId(id);

        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        model.addAttribute("message", "No Comments Found!!!");
        return "showAllCommentsForTask";
    }

    // Add/POST New Comment from Task editing Form
    @PostMapping("/tasks/{id}/comments")
    public String createNewCommentFromCommentsView(@PathVariable("id") Long id,
                                   @Valid TaskComment newTaskComment,
                                   BindingResult result, Errors errors, Model model) {
        if (result.hasErrors()) {
            return "addNewTaskCommentForm";
        }

        Task taskToUpdate = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

        String username = getCurrentLoggedUsername();
        User author = userService.findUserByUsername(username).orElseThrow();
        newTaskComment.setAuthor(author);

        TaskComment taskComment = new TaskComment();
        taskComment.setCommentContent(newTaskComment.getCommentContent());
        taskComment.setAuthor(author);
        taskComment.setCreatedDate(LocalDateTime.now());

        taskToUpdate.getTaskComments().add(taskComment);

        TaskComment taskCommentToAdd = taskService.findTaskById(id).map(
                t -> {
                    t.getTaskComments().add(taskComment);
                    return taskCommentService.save(taskComment);
                }).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));


        model.addAttribute("comment", taskComment);
        model.addAttribute("addedComment", taskCommentToAdd);
        return "redirect:/tasks/{id}/comments";
    }

    // Open Comment form
    @GetMapping("/tasks/{id}/addNewComment")
    public String addNewComment(@PathVariable("id") Long id, Model model) {

        Task task = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

        model.addAttribute("task", task);
        model.addAttribute("comment", new TaskComment());
        return "addNewTaskCommentForm";
    }

    @GetMapping("/tasks/{id}/comments/{commentId}")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {

        taskCommentService.delete(taskCommentService.findCommentById(commentId));
        return "redirect:/tasks/{id}/comments";
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
