package com.book.bookcore.dto;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookCreateDTO {

    @NotBlank(message = "图书标题不能为空")
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;

    private String subtitle;

    @NotBlank(message = "作者不能为空")
    private String author;

    private String translator;

    @NotBlank(message = "ISBN不能为空")
    @Pattern(regexp = "^[0-9-]{10,20}$", message = "ISBN格式不正确")
    private String isbn;

    @NotBlank(message = "出版社不能为空")
    private String publisher;

    private LocalDate publishDate;

    private String language = "中文";

    private Integer pageCount;

    private String description;

    private String coverImage;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.00", message = "价格不能小于0")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "折扣价不能小于0")
    private BigDecimal discountPrice;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    private String keywords;

    private List<Long> tagIds;

    private List<BookImageDTO> images;

    @Data
    public static class BookImageDTO {
        private String imageUrl;
        private Integer imageType = 1;
        private Integer sortOrder = 0;
    }
}
