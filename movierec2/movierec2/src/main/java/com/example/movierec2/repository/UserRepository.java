package com.example.movierec2.repository;

import java.nio.file.LinkOption;

import com.example.movierec2.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
