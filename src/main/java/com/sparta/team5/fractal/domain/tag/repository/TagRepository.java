package com.sparta.team5.fractal.domain.tag.repository;

import com.sparta.team5.fractal.domain.tag.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}


