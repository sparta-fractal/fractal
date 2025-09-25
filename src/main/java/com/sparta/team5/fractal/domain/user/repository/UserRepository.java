package com.sparta.team5.fractal.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.team5.fractal.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
