package com.sparta.team5.fractal.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductUpdateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    String title,

    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    BigDecimal price,

    @NotBlank(message = "설명은 필수입니다.")
    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    String description,

    List<@NotNull(message = "카테고리 ID는 필수입니다.") Long> categoryIds,

    @NotEmpty(message = "태그는 최소 1개 이상이어야 합니다.")
    List<@Size(max = 100, message = "각 태그는 100자를 초과할 수 없습니다.") String> tags
) {}
