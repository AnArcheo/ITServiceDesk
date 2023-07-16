package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model){
        List<User> listUsers = userService.listAllUsers();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    //TODO: check which form to use
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        userService.registerDefaultUser(user);

        return "register_success";
    }


    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model){
        Optional<User> user = userService.findUserById(id);
        List<Role> listRoles = userService.listAllRoles();
        if(user.isEmpty()){
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";

    }


    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.saveUser(user);

        return "redirect:/users";
    }
}
