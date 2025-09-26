package com.sparta.team5.fractal.domain.product.entity;

import com.sparta.team5.fractal.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 200)
    private String tags;

    private Product(String title, BigDecimal price, String description, String tags) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.tags = tags;
    }

    public static Product of(String title, BigDecimal price, String description, String tags) {
        return new Product(title, price, description, tags);
    }

    public void update(String title, BigDecimal price, String description, String tags) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.tags = tags;
    }
}
