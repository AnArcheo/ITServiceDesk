package com.project.itservicedesk.dto_toRemove;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserDTO(
        Long id,
        @NotBlank
        @NotEmpty
        @Size(max = 30)
        String username,

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
        List<String> roles,
        List<String> projects
) {
}
