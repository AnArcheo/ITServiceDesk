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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class BugController {

    private static final int[] PAGE_SIZES = {5, 10, 15, 25};



    private final BugService bugService;
    private final BugAttachmentService bugAttachmentService;
    private final BugCommentService bugCommentService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/bugs")
    public String showAllBugsWithPagingAndSorting(Model model,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort) {

        try {
            List<Bug> bugs = new ArrayList<Bug>();
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(order));

            Page<Bug> pageBugs;

            if (keyword == null) {
                pageBugs = bugService.getAll(pageable);
            } else {
                pageBugs = bugService.searchRepository(keyword, pageable);
                model.addAttribute("keyword", keyword);
                if (pageBugs.isEmpty()) {
                    model.addAttribute("message", "No Bugs Found!!!");
                }
            }
            bugs = pageBugs.getContent();

            List<BugAttachment> attachments = bugAttachmentService.getAllAttachments().toList();
            List<BugComment> comments = bugCommentService.getAllComments();

            model.addAttribute("pageName", "bugs"); //for paging and sorting
            model.addAttribute("page", page);
            model.addAttribute("bugList", bugs);
            model.addAttribute("attachments", attachments);
            model.addAttribute("comments", comments);
            model.addAttribute("currentPage", pageBugs.getNumber() + 1);
            model.addAttribute("totalItems", pageBugs.getTotalElements());
            model.addAttribute("totalPages", pageBugs.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizesList", PAGE_SIZES);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "bugs";
    }


    @GetMapping("/bugs/{id}/details")
    public String showBugDetails(@PathVariable("id") Long bugId, Model model) {
        Optional<Bug> bug = bugService.findBugById(bugId);
        List<BugComment> comments = bugCommentService.getAllCommentsByBugId(bugId);
        List<BugAttachment> attachments = bugAttachmentService.getAllAttachmentsForBugId(bugId);
        model.addAttribute("bug", bug);
        model.addAttribute("bug_comments", comments);
        model.addAttribute("bug_attachments", attachments);

        return "bugDetails";//TODO: create html
    }

    @RolesAllowed({"ADMIN" ,"MANAGER"})
    @GetMapping("/bugs/delete/{id}")
    public String deleteBug(@PathVariable("id") Long id) {
        bugService.delete(bugService.findBugById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bug does not exist")));
//        bugService.deleteById(id);
        return "redirect:/bugs";
    }

    @GetMapping("/bugs/edit/{id}")
    public String editBug(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bug", bugService.findBugById(id));

        //load list of users before opening new form
        List<User> listOfUsers = userService.listAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        //load list of projects
        List<Project> listOfProjects = projectService.getAllProjects();
        model.addAttribute("listOfProjects", listOfProjects);

        return "editBugForm";
    }
}
