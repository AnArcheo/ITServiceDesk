package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.User;
import com.project.itservicedesk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserPasswordController {

    private final UserService userService;
    // forgot Password
    @GetMapping("/loadForgotPassword")
    public String showForgotPasswordForm(){

        return "forgotPasswordForm";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(@RequestParam String email, Model model){
        Optional<User> userByEmail = userService.findUserByEmail(email);

        if(userByEmail.isPresent()){
            return "redirect:/loadResetPassword/" + userByEmail.get().getId(); //TODO: change id to token? "/resetPassword?token=" + token
        }else{
            model.addAttribute("error", ("User with email: " + email + " Not Found!"));
        }
        return "forgotPasswordForm";
    }

// reset Password
    @GetMapping("/loadResetPassword/{id}")
    public String showResetPasswordForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id).orElseThrow();

        model.addAttribute("user", user);
        return "resetPasswordForm";
    }

    @PostMapping("/changePassword")
    public String processResetPassword(@RequestParam Long id, @RequestParam String newPassword, Model model) { //@RequestParam Long id, //TODO: add check of new password and confirm password

        User user = userService.findUserById(id).orElseThrow();

        userService.updateUserPassword(user, newPassword);

        model.addAttribute("user", user);

        model.addAttribute("message", "Password Changed Successfully!");

        return "message";
    }


}
