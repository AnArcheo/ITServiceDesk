package com.project.itservicedesk.dto_toRemove.mapper;

import com.project.itservicedesk.dto_toRemove.UserDTO;
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

}
