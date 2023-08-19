package com.project.itservicedesk.dto_toRemove;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO{

        @NotBlank
        @NotEmpty
        @Size(max = 30)
        private String username;

        @NotBlank
        @NotEmpty
        @Size(max = 256)
        private String password;

        @NotBlank
        @NotEmpty
        @Email
        @Size(max = 45)
        private String email;

        @NotBlank
        @NotEmpty
        @Size(max = 20)
        private String firstname;

        @NotBlank
        @NotEmpty
        @Size(max = 20)
        private String lastname;


}
