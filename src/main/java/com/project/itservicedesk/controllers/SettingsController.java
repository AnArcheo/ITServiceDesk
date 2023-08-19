package com.project.itservicedesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @GetMapping("/loadSiteUnderConstruction")
    public String showUnderConstructionPage(){
        return "underConstruction";
    }
}
