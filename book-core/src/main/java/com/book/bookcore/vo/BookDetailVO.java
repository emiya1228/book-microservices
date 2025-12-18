package com.book.bookcore.vo;

import com.book.bookcore.entity.BookImage;
import com.book.bookcore.entity.BookTag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookDetailVO {

    private Long id;
    private String title;
    private String subtitle;
    private String author;
    private String translator;
    private String isbn;
    private String publisher;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    private String language;
    private Integer pageCount;
    private String description;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private BigDecimal actualPrice;
    private Integer stock;
    private Integer soldCount;
    private Integer viewCount;
    private Long categoryId;
    private String categoryName;
    private String keywords;
    private Integer status;
    private String statusDesc;
    private BigDecimal rating;
    private Integer ratingCount;
    private Boolean recommended;
    private Boolean hot;
    private Boolean inStock;
    private Boolean onSale;
    private List<BookTag> tags;
    private List<BookImage> images;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
