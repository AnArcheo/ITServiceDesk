package com.project.itservicedesk.repositories;


import com.project.itservicedesk.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //TODO: JOIN with ROLES and Projects
    @Query(value = "SELECT * FROM users WHERE CONCAT(is_active, first_name, last_name, username, email) ILIKE (CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<User> searchRepository(String keyword, Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.username=?1")
    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE u.email=?1")
    Optional<User> findByEmail(String email);

    Optional<User> findByUserToken(String token);



}
