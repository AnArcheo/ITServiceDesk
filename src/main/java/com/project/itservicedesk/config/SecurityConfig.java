package com.project.itservicedesk.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();

    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)// (csrf) -> csrf.disable() or provide method reference
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/", "/login", "/logout" ,"/message","/forgotPassword", "/loadSiteUnderConstruction",
                                "/loadResetPassword/**", "/loadForgotPassword","/changePassword",
                                "/resetPassword","/user/register", "/loadAccessDeniedPage", "/userProfile/**",
                                "/error/**", "/images/avatars/**", "/css/**", "/js/**",
                                "/webjars/**", "/resources/**", "/static/**", "/api/**")
                                .permitAll()

                        .requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")

                        .requestMatchers( "/tasks/**", "/bugs/**", "/images/**")

                        .hasAnyAuthority("USER", "ADMIN", "MANAGER", "IT TECHNICIAN", "DEVELOPER", "TESTER") // TODO: add other endpoints

                        .requestMatchers("/users/**", "/projects/**", "/editProjectForm").hasAuthority("ADMIN") // TODO: add other user roles
                        .requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                )
                .formLogin(form -> form.loginPage("/login").permitAll())

                 // form -> form.permitAll() or provide method reference // AbstractAuthenticationFilterConfigurer::permitAll
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(error -> error.accessDeniedHandler(accessDeniedHandler()))
                .build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDenied();
    }
    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityDisabled(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // or provide method reference
                .authorizeHttpRequests(requests -> requests
                        .anyRequest()
                        .permitAll()
                )
                .build();
    }
}
