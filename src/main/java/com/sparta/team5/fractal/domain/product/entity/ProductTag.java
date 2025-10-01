package com.sparta.team5.fractal.domain.product.entity;

import java.time.LocalDateTime;

import com.sparta.team5.fractal.domain.tag.entity.Tag;

import jakarta.persistence.Column;
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
@Table(name = "product_tag",
	uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "tag_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id", nullable = false)
	private Tag tag;

	@Column(name = "mapped_at", nullable = false)
	private LocalDateTime mappedAt;

	private ProductTag(Product product, Tag tag) {
		this.product = product;
		this.tag = tag;
		this.mappedAt = LocalDateTime.now();
	}

	public static ProductTag of(Product product, Tag tag) {
		return new ProductTag(product, tag);
	}

	// 삭제
	public void remove() {
		this.product = null;
		this.tag = null;
	}
}


