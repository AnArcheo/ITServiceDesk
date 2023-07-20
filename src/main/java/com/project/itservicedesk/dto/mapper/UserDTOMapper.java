package com.project.itservicedesk.dto.mapper;

import com.project.itservicedesk.dto.UserDTO;
import com.project.itservicedesk.dto.UserRegistrationDTO;
import com.project.itservicedesk.models.Project;
import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper {

    public UserDTO userToUserDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .toList(),
                user.getProjects().stream()
                        .map(Project::getProjectName)
                        .toList()
        );
    }

    //TODO: use method userRegistrationDTOtoUser in User Service in add/create new user
    // TODO: use userRegistrationDTO in Controller - 437 video
    public User userRegistrationDTOtoUser(UserRegistrationDTO userRegistrationDTO) {
//        return new User(
//              userRegistrationDTO.username(),
//              userRegistrationDTO.password(),
//              userRegistrationDTO.email(),
//              userRegistrationDTO.firstname(),
//              userRegistrationDTO.lastname(),
//              userRegistrationDTO.gender()
//        );

        return User.builder()
                .username(userRegistrationDTO.username())
                .password(userRegistrationDTO.password())
                .email(userRegistrationDTO.email())
                .firstname(userRegistrationDTO.firstname())
                .lastname(userRegistrationDTO.lastname())
                .gender(userRegistrationDTO.gender())
                .build();
    }


}
