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

/**
 * Product와 Category 간의 다대다 관계를 나타내는 조인 엔티티
 */
@Entity
@Getter
@Table(
    name = "product_category", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "category_id"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * ProductCategory 생성자
     * 
     * @param product 연결할 상품
     * @param category 연결할 카테고리
     * @throws IllegalArgumentException product 또는 category가 null인 경우
     */
    private ProductCategory(Product product, Category category) {
        validateNotNull(product, "상품은 null일 수 없습니다");
        validateNotNull(category, "카테고리는 null일 수 없습니다");
        
        this.product = product;
        this.category = category;
    }

    /**
     * ProductCategory 인스턴스를 생성하는 팩토리 메서드
     * 
     * @param product 연결할 상품
     * @param category 연결할 카테고리
     * @return ProductCategory 인스턴스
     * @throws IllegalArgumentException product 또는 category가 null인 경우
     */
    public static ProductCategory of(Product product, Category category) {
        return new ProductCategory(product, category);
    }

    /**
     * null 검증 헬퍼 메서드
     * 
     * @param value 검증할 값
     * @param message 예외 메시지
     * @throws IllegalArgumentException 값이 null인 경우
     */
    private static void validateNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 동등성 비교를 위한 equals 메서드
     * 
     * @param obj 비교할 객체
     * @return 동등한지 여부
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ProductCategory that = (ProductCategory) obj;
        
        if (product == null || category == null) return false;
        if (that.product == null || that.category == null) return false;
        
        return product.equals(that.product) && category.equals(that.category);
    }

    /**
     * 해시코드 생성
     * 
     * @return 해시코드
     */
    @Override
    public int hashCode() {
        if (product == null || category == null) return 0;
        return product.hashCode() * 31 + category.hashCode();
    }

    /**
     * 문자열 표현
     * 
     * @return 문자열 표현
     */
    @Override
    public String toString() {
        return String.format("ProductCategory{id=%d, productId=%d, categoryId=%d}", 
            id, 
            product != null ? product.getId() : null, 
            category != null ? category.getId() : null
        );
    }
}
