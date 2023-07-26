package com.example.aspblindajes.repository;

import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
