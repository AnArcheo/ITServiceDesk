package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.UserService;
import org.springframework.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/user/register")
    public String showRegistrationForm(User user, Model model) {
        model.addAttribute("user", user);

        return "registerNewUserForm";
    }
    @PostMapping("/user/register")
    public String processRegister(@Valid @ModelAttribute("user") User user,  BindingResult result, Model model, Errors errors) {

        Optional<User> existingUserByUsername = userService.findUserByUsername(user.getUsername());
        Optional<User> existingUserByEmail = userService.findUserByEmail(user.getEmail());

        if(existingUserByUsername.isPresent()){
            result.rejectValue("username", null,"Username already exists. Provide different username.");
        }
        if(existingUserByEmail.isPresent()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email. Please provide different email.");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "registerNewUserForm";
        }

        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        model.addAttribute("errors", errors);
        model.addAttribute("user", user);

        userService.registerDefaultUser(user);
        model.addAttribute("message", "User Registered Successfully !!!");
        return "message";
    }

}
