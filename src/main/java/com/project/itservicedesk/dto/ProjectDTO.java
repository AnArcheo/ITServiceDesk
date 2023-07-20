package com.project.itservicedesk.dto;

import com.project.itservicedesk.models.Company;
import com.project.itservicedesk.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectDTO(

        @NotBlank
        @NotEmpty
        @Size(max = 255)
        String projectName,

        @NotBlank
        @NotEmpty
        @Size(max = 255)
        Company company,

        @NotBlank
        @NotEmpty
        @Size(max = 255)
        String projectManagerName,

        List<User> users
) {
}
