package com.sparta.team5.fractal.domain.search.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "searches")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long count = 0L;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
