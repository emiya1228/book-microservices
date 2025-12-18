package com.book.bookcore.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryTreeVO {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private Integer sortOrder;
    private Integer status;
    private List<CategoryTreeVO> children = new ArrayList<CategoryTreeVO>();
}
