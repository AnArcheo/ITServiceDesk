package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.UserNotFoundException;
import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.BugService;
import com.project.itservicedesk.services.TaskService;
import com.project.itservicedesk.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final TaskService taskService;
    private final BugService bugService;

    @GetMapping("/userProfile/{username}")
    public String showUserProfile(@PathVariable("username") String username, Model model) {

        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " Not Found!"));
        Long userId = user.getId();
        List<Task> listOfUserTasks = taskService.getAllTasksCreatedOrAssignedTo(userId);

        List<Bug> listOfBugsCreated = bugService.getAllBugssCreatedOrAssignedTo(userId);
        Long numOfTasksCreated = listOfUserTasks.stream().filter(c -> c.getCreator().getId().equals(userId)).count();
        Long numOfTasksAssigned = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)).count();

        Long numOfTasksNotStarted = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Not Started")).count();
        Long numOfTasksInProgress = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("In Progress")).count();
        Long numOfTasksOnHold = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("On Hold")).count();
        Long numOfTasksCancelled = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Cancelled")).count();
        Long numOfTasksPostponed = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Postponed")).count();
        Long numOfTasksCompleted = listOfUserTasks.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Completed")).count();

        Long numOfBugsReported = listOfBugsCreated.stream().filter(c -> c.getReporter().getId().equals(userId)).count();
        Long numOfBugsAssigned = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)).count();

        Long numOfBugsNotStarted = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Not Started")).count();
        Long numOfBugsInProgress = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("In Progress")).count();
        Long numOfBugsOpen = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Open")).count();
        Long numOfBugsToBeTested = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("To Be Tested")).count();
        Long numOfBugsReviewing = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Reviewing")).count();
        Long numOfBugsCancelled = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Cancelled")).count();
        Long numOfBugsNotABug = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Not a Bug")).count();
        Long numOfBugsNotAnIssue = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Not an Issue")).count();
        Long numOfBugsPostponed = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Postponed")).count();
        Long numOfBugsClosed = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Closed")).count();
        Long numOfBugsResolved = listOfBugsCreated.stream().filter(c -> c.getAssignee().getId().equals(userId)
                && c.getStatus().getDisplayLabel().equals("Resolved")).count();

        model.addAttribute("user", user);
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

        return "userProfile";
    }

}
