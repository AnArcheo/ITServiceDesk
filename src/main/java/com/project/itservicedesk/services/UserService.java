package com.project.itservicedesk.services;

import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.RoleRepository;
import com.project.itservicedesk.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private RoleRepository roleRepository;

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    public void registerDefaultUser(User user){
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);
        userRepository.save(user);
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }


    public List<Role> listAllRoles(){
        return roleRepository.findAll();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    /**
     * Methods to return Pagination
     */
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
