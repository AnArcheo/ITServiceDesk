package com.project.itservicedesk.services;

import com.project.itservicedesk.models.Role;
import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserSecurityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<SimpleGrantedAuthority> grantedAuthorities = getUserAuthorities(user.getRoles());

        return createUserAuthentication(user, grantedAuthorities);
    }

    private UserDetails createUserAuthentication(User user, List<SimpleGrantedAuthority> grantedAuthorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getIsActive(),
                true,
                true,
                true,
                grantedAuthorities
        );
    }

    private List<SimpleGrantedAuthority> getUserAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .distinct()
                .toList();
    }
}
