package com.project.itservicedesk.repositories;

import com.project.itservicedesk.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //    Page<User> searchRepository(String keyword, Pageable pageable);
    List<User> findAllUsersByUsername(String username);
    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE u.email=?1")
    User findByEmail(String email);

}
