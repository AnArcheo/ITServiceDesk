package com.project.itservicedesk.services;

import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }
}
