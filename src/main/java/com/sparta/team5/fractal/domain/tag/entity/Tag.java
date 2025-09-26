package com.sparta.team5.fractal.domain.tag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "views")
    private Long views;

    private Tag(String name) {
        this.name = name;
        this.views = 0L;
    }

    public static Tag of(String name) {
        return new Tag(name);
    }
}


