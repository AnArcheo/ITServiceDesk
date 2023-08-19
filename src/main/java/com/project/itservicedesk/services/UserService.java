package com.project.itservicedesk.services;


import com.project.itservicedesk.exception.UserNotFoundException;
import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.RoleRepository;
import com.project.itservicedesk.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final UserRegisterDTOMapper userRegisterDTOMapper; // disabled


    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username){
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByUserToken(String token){
        return userRepository.findByUserToken(token);
    }

    public void updateUserToken(String token, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("User with email " + email + " Not Found"));

        if(user != null){
            user.setUserToken(token);
            userRepository.save(user);
        }

    }

    public void updateUserPassword(User user, String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setUserToken(null);
        userRepository.save(user);
    }


    public void registerDefaultUser(User user){
        Role userRole = roleRepository.findByName("USER");
        user.setIsActive(true);
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

    //  using DTO - disabled
//    public void registerDefaultUser(UserRegistrationDTO userRegistrationDTO){
//        User user = userRegisterDTOMapper.userRegistrationDTOtoUser(userRegistrationDTO);
//        userRepository.save(user);
//    }

    public String createRandomToken(){
        return UUID.randomUUID().toString();
    }


    public Page<User> searchRepository(String keyword, Pageable pageable) {
        return userRepository.searchRepository(keyword, pageable);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
