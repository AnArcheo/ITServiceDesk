package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.ResourceNotFoundException;
import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.BugComment;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.BugCommentService;
import com.project.itservicedesk.services.BugService;
import com.project.itservicedesk.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BugCommentController {

    private final BugService bugService;
    private final BugCommentService bugCommentService;
    private final UserService userService;

    //TODO: ad pagination and sorting plus add this to repository
    // show comments from Bug page
    @GetMapping("/bugs/{id}/comments")
    public String showComments(@PathVariable("id") Long id, Model model) {

        Bug bug = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

        List<BugComment> comments = bugCommentService.getAllCommentsByBugId(id);

        model.addAttribute("bug", bug);
        model.addAttribute("comments", comments);
        model.addAttribute("message", "No Comments Found!!!");
        return "showAllCommentsForBug";
    }

    // Add/POST New Comment from Bug editing Form
    @PostMapping("/bugs/{id}/comments")
    public String createNewCommentFromCommentsView(@PathVariable("id") Long id,
                                   @Valid BugComment newBugComment,
                                   BindingResult result, Errors errors, Model model) {
        if (result.hasErrors()) {
            return "addNewBugCommentForm";
        }

        Bug bugToUpdate = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

        String username = getCurrentLoggedUsername();
        User reporter = userService.findUserByUsername(username).orElseThrow();
        newBugComment.setAuthor(reporter);

        BugComment bugComment = new BugComment();
        bugComment.setCommentContent(newBugComment.getCommentContent());
        bugComment.setAuthor(reporter);
        bugComment.setCreatedDate(LocalDateTime.now());

        bugToUpdate.getBugComments().add(bugComment);

        BugComment bugCommentToAdd = bugService.findBugById(id).map(
                t -> {
                    t.getBugComments().add(bugComment);
                    return bugCommentService.save(bugComment);
                }).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));


        model.addAttribute("comment", bugComment);
        model.addAttribute("addedComment", bugCommentToAdd);
        return "redirect:/bugs/{id}/comments";
    }

    // Open Comment form
    @GetMapping("/bugs/{id}/addNewComment")
    public String addNewComment(@PathVariable("id") Long id, Model model) {

        Bug bug = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

        model.addAttribute("bug", bug);
        model.addAttribute("comment", new BugComment());
        return "addNewBugCommentForm";
    }

    @GetMapping("/bugs/{id}/comments/{commentId}")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {

        bugCommentService.delete(bugCommentService.findCommentById(commentId));
        return "redirect:/bugs/{id}/comments";
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
