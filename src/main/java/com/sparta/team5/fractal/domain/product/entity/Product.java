package com.sparta.team5.fractal.domain.product.entity;

import com.sparta.team5.fractal.common.entity.BaseEntity;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
//@SQLRestriction("is_deleted = false")
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductTag> productTags = new HashSet<>();

    private Product(String title, BigDecimal price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public static Product of(String title, BigDecimal price, String description) {
        return new Product(title, price, description);
    }

    public void update(String title, BigDecimal price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public void replaceTags(Set<Tag> tags) {
        this.productTags.clear();
        if (tags == null) {
            return;
        }
        for (Tag tag : tags) {
            this.productTags.add(ProductTag.of(this, tag));
        }
    }

    public void addTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("태그는 null일 수 없습니다");
        }
        boolean exists = this.productTags.stream()
                .anyMatch(pt -> pt.getTag().equals(tag));
        if (!exists) {
            this.productTags.add(ProductTag.of(this, tag));
        }
    }

    public void removeTag(Tag tag) {
        if (tag == null) {
            return;
        }
        this.productTags.removeIf(pt -> pt.getTag().equals(tag));
    }

    public void replaceCategories(Set<Category> categories) {
        this.productCategories.clear();
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            this.productCategories.add(ProductCategory.of(this, category));
        }
    }

    public void addCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("카테고리는 null일 수 없습니다");
        }
        boolean exists = this.productCategories.stream()
                .anyMatch(pc -> pc.getCategory().equals(category));
        if (!exists) {
            this.productCategories.add(ProductCategory.of(this, category));
        }
    }

    public void removeCategory(Category category) {
        if (category == null) {
            return;
        }
        this.productCategories.removeIf(pc -> pc.getCategory().equals(category));
    }
}
