package com.sparta.team5.fractal.domain.tag.repository;

import com.sparta.team5.fractal.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 태그 이름으로 {@link Tag} 엔티티를 조회합니다.
     *
     * @param name 조회할 태그 이름
     * @return 해당 이름을 가진 {@link Tag}, 존재하지 않으면 {@link Optional#empty()}
     */
    Optional<Tag> findByName(String name);
}


