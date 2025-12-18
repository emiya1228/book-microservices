package com.book.bookcore.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookSearchVO {

    private String keyword;
    private String author;
    private String publisher;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer status;
    private Boolean recommended;
    private Boolean hot;
    private Long tagId;
    private String sortField = "create_time";
    private String sortOrder = "desc";
}
