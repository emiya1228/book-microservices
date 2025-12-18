package com.book.booksearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ES文档实体类
 * 对应MySQL的book表
 *
 * 注意：ES实体类与MySQL实体类分开，因为：
 * 1. 字段类型可能不同（如日期格式）
 * 2. ES需要分词、索引等特殊配置
 * 3. 可以包含冗余字段提高查询性能
 */
@Data
@Document(
        indexName = "books",
        createIndex = false // 手动创建索引，不自动创建
)
@Setting(settingPath = "/elasticsearch/settings/books-settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings/books-mapping.json")
public class BookDocument {

    // ==================== 基础字段 ====================

    /**
     * 图书ID（与MySQL一致）
     */
    @Id
    private Long id;

    /**
     * 图书标题 - 需要中文分词和拼音搜索
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text,
                    analyzer = "ik_max_word",  // 索引时使用最细粒度分词
                    searchAnalyzer = "ik_smart", // 搜索时使用智能分词
                    copyTo = "combined_search"), // 复制到combined_search字段
            otherFields = {
                    @InnerField(suffix = "pinyin",
                            type = FieldType.Text,
                            analyzer = "pinyin"),  // 拼音搜索
                    @InnerField(suffix = "keyword",
                            type = FieldType.Keyword,  // 精确匹配
                            ignoreAbove = 256),
                    @InnerField(suffix = "standard",
                            type = FieldType.Text,
                            analyzer = "standard")  // 英文分词
            }
    )
    private String title;

    /**
     * 副标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String subtitle;

    /**
     * 作者 - 可能需要按作者名搜索
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text,
                    analyzer = "ik_max_word",
                    searchAnalyzer = "ik_smart",
                    copyTo = "combined_search"),
            otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword)
    )
    private String author;

    /**
     * 译者
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String translator;

    /**
     * ISBN编号 - 精确匹配，不分词
     */
    @Field(type = FieldType.Keyword)
    private String isbn;

    /**
     * 出版社
     */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword)
    )
    private String publisher;

    /**
     * 出版日期 - 日期类型，便于范围查询
     */
    @Field(type = FieldType.Date,
            format = DateFormat.date,  // 只存储日期，不包含时间
            pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    /**
     * 语言
     */
    @Field(type = FieldType.Keyword)
    private String language;

    /**
     * 页数
     */
    @Field(type = FieldType.Integer)
    private Integer pageCount;

    /**
     * 图书描述 - 长文本，需要全文检索
     */
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word",
            searchAnalyzer = "ik_smart",
            copyTo = "combined_search")
    private String description;

    /**
     * 封面图片URL - 不索引，只存储
     */
    @Field(type = FieldType.Keyword, index = false)
    private String coverImage;

    /**
     * 价格 - 用于范围查询和排序
     */
    @Field(type = FieldType.Double)
    private BigDecimal price;

    /**
     * 折扣价
     */
    @Field(type = FieldType.Double)
    private BigDecimal discountPrice;

    /**
     * 实际价格（计算字段，不在MySQL中）
     */
    @Field(type = FieldType.Double)
    private BigDecimal actualPrice;

    /**
     * 库存数量
     */
    @Field(type = FieldType.Integer)
    private Integer stock;

    /**
     * 销量 - 用于排序和热门推荐
     */
    @Field(type = FieldType.Integer)
    private Integer soldCount;

    /**
     * 浏览量 - 用于热门推荐
     */
    @Field(type = FieldType.Integer)
    private Integer viewCount;

    /**
     * 分类ID - 用于过滤
     */
    @Field(type = FieldType.Long)
    private Long categoryId;

    /**
     * 分类名称（冗余字段，避免联表查询）
     */
    @Field(type = FieldType.Keyword)
    private String categoryName;

    /**
     * 关键词 - 逗号分隔，需要分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String keywords;

    /**
     * 状态：0-下架，1-上架，2-缺货
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 评分（0-5分）
     */
    @Field(type = FieldType.Double)
    private BigDecimal rating;

    /**
     * 评分人数
     */
    @Field(type = FieldType.Integer)
    private Integer ratingCount;

    /**
     * 是否推荐
     */
    @Field(type = FieldType.Boolean)
    private Boolean recommended;

    /**
     * 是否热门
     */
    @Field(type = FieldType.Boolean)
    private Boolean hot;

    // ==================== 时间字段 ====================

    /**
     * 创建时间 - 用于排序和新书推荐
     */
    @Field(type = FieldType.Date,
            format = DateFormat.date_hour_minute_second,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date,
            format = DateFormat.date_hour_minute_second,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    @Field(type = FieldType.Long)
    private Long createBy;

    /**
     * 更新人ID
     */
    @Field(type = FieldType.Long)
    private Long updateBy;

    // ==================== ES特有字段（不在MySQL中） ====================

    /**
     * 标签列表（嵌套对象，从book_tag表关联而来）
     */
    @Field(type = FieldType.Nested)
    private List<Tag> tags;

    /**
     * 图片列表（嵌套对象）
     */
    @Field(type = FieldType.Nested)
    private List<Image> images;

    /**
     * 分类路径（用于层级搜索，如：计算机/编程/Java）
     */
    @Field(type = FieldType.Keyword)
    private List<String> categoryPath;

    /**
     * 搜索权重字段（用于相关性排序）
     */
    @Field(type = FieldType.Double)
    private Double searchWeight;

    /**
     * 地理位置（如果需要按距离搜索）
     */
    @GeoPointField
    private GeoPoint location;

    /**
     * 综合搜索字段（将title、author、description等字段复制到这里）
     * 用于跨字段搜索，提高性能
     */
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word",
            searchAnalyzer = "ik_smart",
            index = false)  // 不单独索引，只用于搜索
    private String combinedSearch;

    /**
     * 拼音标题（用于拼音搜索）
     */
    @Field(type = FieldType.Text, analyzer = "pinyin")
    private String titlePinyin;

    /**
     * 拼音作者（用于拼音搜索）
     */
    @Field(type = FieldType.Text, analyzer = "pinyin")
    private String authorPinyin;

    /**
     * 出版年份（从publishDate提取，便于按年聚合）
     */
    @Field(type = FieldType.Integer)
    private Integer publishYear;

    /**
     * 是否在库（计算字段：stock > 0）
     */
    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    /**
     * 热度分数（计算字段，综合soldCount、viewCount、rating等）
     */
    @Field(type = FieldType.Double)
    private Double hotScore;

    // ==================== 嵌套类 ====================

    /**
     * 标签嵌套类
     */
    @Data
    public static class Tag {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Keyword)
        private String color;

        @Field(type = FieldType.Text, analyzer = "ik_max_word")
        private String nameText;  // 用于搜索

        public Tag() {}

        public Tag(Long id, String name, String color) {
            this.id = id;
            this.name = name;
            this.color = color;
            this.nameText = name;
        }
    }

    /**
     * 图片嵌套类
     */
    @Data
    public static class Image {
        @Field(type = FieldType.Long)
        private Long id;

        @Field(type = FieldType.Keyword, index = false)
        private String imageUrl;

        @Field(type = FieldType.Integer)
        private Integer imageType;  // 1-封面，2-详情图，3-预览图

        @Field(type = FieldType.Integer)
        private Integer sortOrder;
    }

    // ==================== 计算方法 ====================

    /**
     * 获取实际价格（如果有折扣价则用折扣价）
     */
    public BigDecimal getActualPrice() {
        if (discountPrice != null && discountPrice.compareTo(BigDecimal.ZERO) > 0
                && discountPrice.compareTo(price) < 0) {
            return discountPrice;
        }
        return price;
    }

    /**
     * 判断是否有库存
     */
    public Boolean getInStock() {
        return stock != null && stock > 0;
    }

    /**
     * 判断是否上架
     */
    public Boolean getOnSale() {
        return status != null && status == 1;
    }

    /**
     * 计算热度分数
     */
    public Double getHotScore() {
        double score = 0.0;

        // 销量权重最高
        if (soldCount != null) {
            score += soldCount * 2.0;
        }

        // 浏览量
        if (viewCount != null) {
            score += viewCount * 0.5;
        }

        // 评分
        if (rating != null) {
            score += rating.doubleValue() * 100;
        }

        // 推荐
        if (recommended != null && recommended) {
            score += 500;
        }

        // 热门
        if (hot != null && hot) {
            score += 1000;
        }

        return score;
    }

    /**
     * 获取出版年份
     */
    public Integer getPublishYear() {
        if (publishDate != null) {
            return publishDate.getYear();
        }
        return null;
    }

    /**
     * 设置综合搜索字段（自动调用）
     */
    public void setCombinedSearch() {
        StringBuilder sb = new StringBuilder();
        if (title != null) sb.append(title).append(" ");
        if (author != null) sb.append(author).append(" ");
        if (subtitle != null) sb.append(subtitle).append(" ");
        if (description != null) sb.append(description).append(" ");
        if (keywords != null) sb.append(keywords).append(" ");
        if (publisher != null) sb.append(publisher).append(" ");
        if (translator != null) sb.append(translator).append(" ");

        this.combinedSearch = sb.toString().trim();
    }

    /**
     * 设置拼音字段（自动调用）
     */
    public void setPinyinFields() {
        if (title != null) {
            // 这里可以调用拼音转换工具
            // this.titlePinyin = PinyinUtil.toPinyin(title);
        }
        if (author != null) {
            // this.authorPinyin = PinyinUtil.toPinyin(author);
        }
    }
}
