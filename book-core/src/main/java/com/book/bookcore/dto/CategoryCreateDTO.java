package com.book.bookcore.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CategoryCreateDTO {

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    private Integer sortOrder = 0;
}
