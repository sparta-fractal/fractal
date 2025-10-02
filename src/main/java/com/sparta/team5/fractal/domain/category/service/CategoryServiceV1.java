package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.domain.category.dto.CategoryCreateRequest;
import com.sparta.team5.fractal.domain.category.dto.CategoryResponse;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.exception.CategoryErrorCode;
import com.sparta.team5.fractal.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceV1 implements CategoryServiceApi {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponse> response = categories.stream()
                .map(CategoryResponse::from)
                .toList();

        return response;
    }

    public CategoryResponse createCategory(CategoryCreateRequest request) {

        validateDuplicateCategoryName(request.name());

        Category parentCategory = null;

        if (request.parentCategoryId() != null) {
            parentCategory = findCategoryOrThrow(request.parentCategoryId());
        }

        Category category = Category.of(request.name(), parentCategory);

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse response = CategoryResponse.from(savedCategory);

        return response;
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryCreateRequest request) {

        Category category = findCategoryOrThrow(categoryId);

        // 중복 카테고리명 체크 (자기 자신 제외)
        if (!category.getName().equals(request.name()) &&
                categoryRepository.existsByName(request.name())) {
            throw new GlobalException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }

        Category parentCategory = null;
        if (request.parentCategoryId() != null) {
            parentCategory = findCategoryOrThrow(request.parentCategoryId());
        }

        category.update(request.name(), parentCategory);

        Category updatedCategory = categoryRepository.save(category);

        CategoryResponse response = CategoryResponse.from(updatedCategory);

        return response;
    }

    public void deleteCategory(Long categoryId) {

        Category category = findCategoryOrThrow(categoryId);

        category.delete();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long Id) {

        Optional<Category> response = categoryRepository.findById(Id);

        return response;
    }

    //헬퍼 메서드
    private void validateDuplicateCategoryName(String categoryName) {

        if (categoryRepository.existsByName(categoryName)) {
            throw new GlobalException(CategoryErrorCode.CATEGORY_NAME_DUPLICATED);
        }
    }

    private Category findCategoryOrThrow(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        return category;
    }
}