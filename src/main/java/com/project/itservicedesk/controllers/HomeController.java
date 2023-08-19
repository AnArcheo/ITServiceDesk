package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping({"/", ""})
    public String welcome(){
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("message");

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
