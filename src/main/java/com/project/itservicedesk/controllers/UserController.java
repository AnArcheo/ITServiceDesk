package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.UserNotFoundException;
import com.project.itservicedesk.models.*;
import com.project.itservicedesk.services.BugService;
import com.project.itservicedesk.services.TaskService;
import com.project.itservicedesk.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    private static final int[] PAGE_SIZES = {5, 10, 15, 25};

    private final UserService userService;
    private final TaskService taskService;
    private final BugService bugService;

    @GetMapping("/users")
    public String listAllUsersWithPagingAndSorting(Model model,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort){



        try {
            List<User> users = new ArrayList<User>();

            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(order));

            Page<User> pageUsers;

            if (keyword == null) {
                pageUsers = userService.getAll(pageable);
            } else {
                pageUsers = userService.searchRepository(keyword, pageable);
                model.addAttribute("keyword", keyword);
                if (pageUsers.isEmpty()) {
                    model.addAttribute("message", "No Users Found!!!");
                }
            }

            users = pageUsers.getContent();

            model.addAttribute("pageName", "users"); //for paging and sorting
            model.addAttribute("page", page);
            model.addAttribute("listUsers", users);
            model.addAttribute("currentPage", pageUsers.getNumber() + 1);
            model.addAttribute("totalItems", pageUsers.getTotalElements());
            model.addAttribute("totalPages", pageUsers.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizesList", PAGE_SIZES);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUserAccount(@PathVariable("id") Long id) {
        userService.delete(userService.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with id " + id)));

        return "redirect:/users";
    }

    @GetMapping("/users/details/{id}")
    public String showUserProfile(@PathVariable("id") Long id, Model model) {

        User user = userService.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " Not Found!"));


        List<Task> listOfUserTasks = taskService.getAllTasksCreatedOrAssignedTo(id);
        List<Bug> listOfBugsCreated = bugService.getAllBugssCreatedOrAssignedTo(id);

        Long numOfTasksCreated = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)).count();
        Long numOfTasksAssigned = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)).count();

        Long numOfTasksNotStarted = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Not Started")).count();
        Long numOfTasksInProgress = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("In Progress")).count();
        Long numOfTasksOnHold = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("On Hold")).count();
        Long numOfTasksCancelled = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Cancelled")).count();
        Long numOfTasksPostponed = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Postponed")).count();
        Long numOfTasksCompleted = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Completed")).count();

        Long numOfBugsReported = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)).count();
        Long numOfBugsAssigned = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)).count();

        Long numOfBugsNotStarted = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Not Started")).count();
        Long numOfBugsInProgress = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("In Progress")).count();
        Long numOfBugsOpen = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Open")).count();
        Long numOfBugsToBeTested = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("To Be Tested")).count();
        Long numOfBugsReviewing = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Reviewing")).count();
        Long numOfBugsCancelled = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Cancelled")).count();
        Long numOfBugsNotABug = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Not a Bug")).count();
        Long numOfBugsNotAnIssue = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Not an Issue")).count();
        Long numOfBugsPostponed = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Postponed")).count();
        Long numOfBugsClosed = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Closed")).count();
        Long numOfBugsResolved = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(id)
                && c.getStatus().getDisplayLabel().equals("Resolved")).count();

        model.addAttribute(user);
        model.addAttribute("userTasks", listOfUserTasks);
        model.addAttribute("userBugs", listOfBugsCreated);

        model.addAttribute("numOfTasksCreated", numOfTasksCreated);
        model.addAttribute("numOfTasksAssigned", numOfTasksAssigned);

        model.addAttribute("numOfTasksNotStarted", numOfTasksNotStarted);
        model.addAttribute("numOfTasksInProgress", numOfTasksInProgress);
        model.addAttribute("numOfTasksOnHold", numOfTasksOnHold);
        model.addAttribute("numOfTasksCancelled", numOfTasksCancelled);
        model.addAttribute("numOfTasksPostponed", numOfTasksPostponed);
        model.addAttribute("numOfTasksCompleted", numOfTasksCompleted);

        model.addAttribute("numOfBugsReported", numOfBugsReported);
        model.addAttribute("numOfBugsAssigned", numOfBugsAssigned);

        model.addAttribute("numOfBugsNotStarted", numOfBugsNotStarted);
        model.addAttribute("numOfBugsInProgress", numOfBugsInProgress);
        model.addAttribute("numOfBugsOpen", numOfBugsOpen);
        model.addAttribute("numOfBugsToBeTested", numOfBugsToBeTested);
        model.addAttribute("numOfBugsCancelled", numOfBugsCancelled);
        model.addAttribute("numOfBugsNotABug", numOfBugsNotABug);
        model.addAttribute("numOfBugsNotAnIssue", numOfBugsNotAnIssue);
        model.addAttribute("numOfBugsPostponed", numOfBugsPostponed);
        model.addAttribute("numOfBugsClosed", numOfBugsClosed);
        model.addAttribute("numOfBugsResolved", numOfBugsResolved);
        model.addAttribute("numOfBugsReviewing", numOfBugsReviewing);

        return "userDetails";
    }
}
