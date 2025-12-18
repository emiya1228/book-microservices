package com.book.bookcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("subtitle")
    private String subtitle;

    @TableField("author")
    private String author;

    @TableField("translator")
    private String translator;

    @TableField("isbn")
    private String isbn;

    @TableField("publisher")
    private String publisher;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("publish_date")
    private LocalDate publishDate;

    @TableField("language")
    private String language;

    @TableField("page_count")
    private Integer pageCount;

    @TableField("description")
    private String description;

    @TableField("cover_image")
    private String coverImage;

    @TableField("price")
    private BigDecimal price;

    @TableField("discount_price")
    private BigDecimal discountPrice;

    @TableField("stock")
    private Integer stock;

    @TableField("sold_count")
    private Integer soldCount;

    @TableField("view_count")
    private Integer viewCount;

    @TableField("category_id")
    private Long categoryId;

    @TableField("keywords")
    private String keywords;

    @TableField("status")
    private Integer status;  // 0-下架，1-上架，2-缺货

    @TableField("rating")
    private BigDecimal rating;

    @TableField("rating_count")
    private Integer ratingCount;

    @TableField("is_recommended")
    private Boolean recommended;

    @TableField("is_hot")
    private Boolean hot;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("create_by")
    private Long createBy;

    @TableField("update_by")
    private Long updateBy;

    // 非数据库字段 - 分类名称
    @TableField(exist = false)
    private String categoryName;

    // 非数据库字段 - 标签列表
    @TableField(exist = false)
    private List<BookTag> tags;

    // 非数据库字段 - 图片列表
    @TableField(exist = false)
    private List<BookImage> images;

    // 计算实际价格（如果有折扣价则用折扣价）
    public BigDecimal getActualPrice() {
        return discountPrice != null && discountPrice.compareTo(BigDecimal.ZERO) > 0
                ? discountPrice : price;
    }

    // 判断是否有库存
    public boolean isInStock() {
        return stock != null && stock > 0;
    }

    // 判断是否上架
    public boolean isOnSale() {
        return status != null && status == 1;
    }
}
