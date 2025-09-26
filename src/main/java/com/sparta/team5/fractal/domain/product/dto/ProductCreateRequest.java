package com.sparta.team5.fractal.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
    String title,

    @NotBlank(message = "가격은 필수입니다.")
    @Size(max = 50, message = "가격은 50자를 초과할 수 없습니다.")
    String price,

    @NotBlank(message = "설명은 필수입니다.")
    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    String description,

    @NotBlank(message = "태그는 필수입니다.")
    @Size(max = 200, message = "태그는 200자를 초과할 수 없습니다.")
    String tags
) {}
