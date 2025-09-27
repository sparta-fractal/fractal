package com.sparta.team5.fractal.domain.tag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.team5.fractal.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Optional<Tag> findByName(String name);
}


