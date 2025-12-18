package com.book.bookcore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryUpdateDTO {

    @NotNull(message = "分类ID不能为空")
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String description;

    private Long parentId;

    private Integer sortOrder;

    private Integer status;
}
