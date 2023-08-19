package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.UserNotFoundException;
import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.ProjectService;
import com.project.itservicedesk.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserFormController {

    private UserService userService;
    private ProjectService projectService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/users/createNewUserAccount")
    public String createNewAccountAndAssignRoles(@ModelAttribute("user") User user, Model model) {
        //load users, roles and projects before opening form
        List<User> listOfUsers = userService.listAllUsers();
        List<Project> listOfProjects = projectService.getAllProjects();
        List<Role> listOfRoles = userService.listAllRoles();

        model.addAttribute("listOfUsers", listOfUsers);
        model.addAttribute("listOfProjects", listOfProjects);
        model.addAttribute("listOfRoles", listOfRoles);

        return "addNewUser";
    }

    @PostMapping("/users/processNewUserAccount")
    public String processNewAccountAndAssignRoles(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {


        Optional<User> existingUserByUsername = userService.findUserByUsername(user.getUsername());
        Optional<User> existingUserByEmail = userService.findUserByEmail(user.getEmail());

        if (existingUserByUsername.isPresent()) {
            result.rejectValue("username", null, "Username already exists. Provide different username.");
        }
        if (existingUserByEmail.isPresent()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email. Please provide different email.");
        }

        //load roles and projects before opening form
        List<Project> listOfProjects = projectService.getAllProjects();
        List<Role> listOfRoles = userService.listAllRoles();

        if (result.hasErrors()) {
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("listRoles", listOfRoles);
            return "addNewUser";
        }

        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        if (StringUtils.hasText(user.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        newUser.setIsActive(true);
        newUser.getProjects().addAll(user.getProjects());
        newUser.getRoles().addAll(user.getRoles());

        model.addAttribute("listOfProjects", listOfProjects);
        model.addAttribute("listRoles", listOfRoles);

        userService.saveUser(newUser);

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        List<Role> listOfRoles = userService.listAllRoles();
        List<Project> listOfProjects = projectService.getAllProjects();

        if (user.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("listOfRoles", listOfRoles);
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("listOfProjects", listOfProjects);
        model.addAttribute("listOfRoles", listOfRoles);

        return "editUserForm";
    }


    @PostMapping("/users/edit/{id}")
    public String saveUser(@PathVariable("id") Long id, @Valid @ModelAttribute User user, Model model, BindingResult result, Errors errors) {

        List<Role> listOfRoles = userService.listAllRoles();
        List<Project> listOfProjects = projectService.getAllProjects();

        model.addAttribute("user", user);
        model.addAttribute("listOfProjects", listOfProjects);
        model.addAttribute("listOfRoles", listOfRoles);

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("listOfProjects", listOfProjects);
            model.addAttribute("listOfRoles", listOfRoles);
            return "editUserForm";
        }

        model.addAttribute("user", user);
        model.addAttribute("listOfProjects", listOfProjects);
        model.addAttribute("listOfRoles", listOfRoles);

        User userToUpdate = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id " + id));

        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.getProjects().clear();
        userToUpdate.getProjects().addAll(user.getProjects());
        userToUpdate.getRoles().clear();
        userToUpdate.getRoles().addAll(user.getRoles());

        userToUpdate.setIsActive(user.getIsActive());

        userService.saveUser(userToUpdate);

        return "redirect:/users";
    }

}
