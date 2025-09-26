package com.sparta.team5.fractal.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
    @NotBlank(message = "카테고리명은 필수입니다.")
    @Size(max = 20, message = "카테고리명은 20자를 초과할 수 없습니다.")
    String name,

    Long parentCategoryId
) {}