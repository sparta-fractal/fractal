package com.sparta.team5.fractal.domain.user.service;

import java.util.Optional;

import com.sparta.team5.fractal.domain.user.entity.User;

public interface UserServiceApi {

	boolean existsByEmail(String email);

	User createUser(String email, String password, String nickname);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

}
