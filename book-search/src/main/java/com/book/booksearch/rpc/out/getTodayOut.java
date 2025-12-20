package com.book.booksearch.rpc.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper=false)
public class getTodayOut {
    private static final long serialVersionUID = 1L;


    private Long id;


    private String title;


    private String subtitle;


    private String author;


    private String translator;


    private String isbn;


    private String publisher;



    private LocalDate publishDate;


    private String language;


    private Integer pageCount;

    private String description;


    private String coverImage;


    private BigDecimal price;


    private BigDecimal discountPrice;


    private Integer stock;


    private Integer soldCount;


    private Integer viewCount;


    private Long categoryId;


    private String keywords;


    private Integer status;  // 0-下架，1-上架，2-缺货


    private BigDecimal rating;


    private Integer ratingCount;


    private Boolean recommended;


    private Boolean hot;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;


    private Long createBy;


    private Long updateBy;
}
