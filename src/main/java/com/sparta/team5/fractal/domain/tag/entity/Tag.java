package com.sparta.team5.fractal.domain.tag.entity;

import com.sparta.team5.fractal.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("is_deleted = false")
public class Tag extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "views")
    private Long views = 0L;

    private Tag(String name) {
        this.name = name;
        this.views = 0L;
    }

    public static Tag from(String name) {
        return new Tag(name);
    }
}


