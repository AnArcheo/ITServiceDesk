package com.project.itservicedesk.dto;

import com.project.itservicedesk.models.Gender;

public record UserRegistrationDTO(
        String username,
        String password,
        String email,
        String firstname,
        String lastname,
        Gender gender
) {
}
