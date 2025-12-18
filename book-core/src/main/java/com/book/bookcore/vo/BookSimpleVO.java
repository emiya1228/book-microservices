package com.book.bookcore.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookSimpleVO {

    private Long id;
    private String title;
    private String author;
    private String coverImage;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private BigDecimal actualPrice;
    private Integer stock;
    private Integer soldCount;
    private Integer status;
    private Boolean inStock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
