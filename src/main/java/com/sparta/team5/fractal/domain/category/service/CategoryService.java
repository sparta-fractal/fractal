package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.category.dto.CategoryCreateRequest;
import com.sparta.team5.fractal.domain.category.dto.CategoryResponse;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.exception.CategoryErrorCode;
import com.sparta.team5.fractal.domain.category.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public CategoryResponse createCategory(CategoryCreateRequest request) {
        // 중복 카테고리명 체크
        if (categoryRepository.existsByName(request.name())) {
            throw new GlobalException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }

        Category parentCategory = null;

        if (request.parentCategoryId() != null) {
            parentCategory = categoryRepository.findById(request.parentCategoryId())
                    .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));
        }

        Category category = Category.of(request.name(), parentCategory);
        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.from(savedCategory);
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryCreateRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        // 중복 카테고리명 체크 (자기 자신 제외)
        if (!category.getName().equals(request.name()) &&
                categoryRepository.existsByName(request.name())) {
            throw new GlobalException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }

        Category parentCategory = null;
        if (request.parentCategoryId() != null) {
            parentCategory = categoryRepository.findById(request.parentCategoryId())
                    .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));
        }

        category.update(request.name(), parentCategory);
        Category updatedCategory = categoryRepository.save(category);

        return CategoryResponse.from(updatedCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        category.delete();

        categoryRepository.save(category);
    }
}
