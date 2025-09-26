package com.sparta.team5.fractal.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    String title,

    @NotNull(message = "가격은 필수입니다.")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    BigDecimal price,

    @NotBlank(message = "설명은 필수입니다.")
    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    String description,

    @NotEmpty(message = "태그는 최소 1개 이상이어야 합니다.")
    List<@Size(max = 100, message = "각 태그는 100자를 초과할 수 없습니다.") String> tags
) {}
