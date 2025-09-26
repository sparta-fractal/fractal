package com.sparta.team5.fractal.domain.product.entity;

import com.sparta.team5.fractal.domain.category.entity.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "category_product", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "category_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

	// 학교 = 카테고리, 학생 = 프로덕트
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// optional = false 할래 말래 할래 말래 category가 null일수도 있음? 있다고 하면 굳이 테이블에 저장 왜 해?
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
