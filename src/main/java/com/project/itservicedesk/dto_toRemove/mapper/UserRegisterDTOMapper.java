package com.project.itservicedesk.dto_toRemove.mapper;

import com.project.itservicedesk.dto_toRemove.UserRegistrationDTO;
import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserRegisterDTOMapper {
    private final RoleRepository roleRepository;
    //TODO: use method userRegistrationDTOtoUser in User Service in add/create new user
    // TODO: use userRegistrationDTO in Controller - 437 video
    public User userRegistrationDTOtoUser(UserRegistrationDTO userRegistrationDTO) {
        Set<Role> roleSet = new HashSet<>();
        Role userRole = Role.builder().id(1L).name("USER").build(); //default
        roleSet.add(userRole);
        User user = User.builder()
                .username(userRegistrationDTO.getUsername())
                .password(userRegistrationDTO.getPassword())
                .email(userRegistrationDTO.getEmail())
                .firstname(userRegistrationDTO.getFirstname())
                .lastname(userRegistrationDTO.getLastname())
                .roles(roleSet)
                .isActive(true)
                .build();

        return user;
    }
}
