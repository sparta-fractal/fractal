package com.sparta.team5.fractal.domain.user.repository;

import com.sparta.team5.fractal.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByEmail(String email);
}
