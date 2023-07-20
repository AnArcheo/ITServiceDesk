package com.project.itservicedesk.dto;

import com.project.itservicedesk.models.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(

        @NotBlank
        @NotEmpty
        @Size(max = 30)
        String username,

        @NotBlank
        @NotEmpty
        @Size(max = 256)
        String password,

        @NotBlank
        @NotEmpty
        @Email
        @Size(max = 45)
        String email,

        @NotBlank
        @NotEmpty
        @Size(max = 20)
        String firstname,

        @NotBlank
        @NotEmpty
        @Size(max = 20)
        String lastname,
        @NotBlank
        @NotEmpty
        @Size(max = 10)
        @Enumerated(value = EnumType.STRING)
        Gender gender
) {
}
