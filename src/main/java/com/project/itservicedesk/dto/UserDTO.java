package com.project.itservicedesk.dto;

import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Role;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String email,
        String firstname,
        String lastname,
        List<String> roles,
        List<String> projects
) {
}
