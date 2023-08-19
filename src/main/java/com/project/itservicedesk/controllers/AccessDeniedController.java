package com.project.itservicedesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {
    @GetMapping("/loadAccessDeniedPage")
    public String getAccessDenied() {
        return "/error/accessDenied";
    }
}
