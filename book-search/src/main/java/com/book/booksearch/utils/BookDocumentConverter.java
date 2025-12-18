package com.book.booksearch.utils;



import com.book.bookcommon.dto.BookDTO;
import com.book.booksearch.entity.BookDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDocumentConverter {

    /**
     * 将MySQL的Book实体转换为ES的BookDocument
     */
    public BookDocument convertToDocument(BookDTO book) {
        if (book == null) {
            return null;
        }

        BookDocument document = new BookDocument();

        // 复制基本属性
        BeanUtils.copyProperties(book, document);

        // 处理特殊字段
        // 1. 实际价格
        document.setActualPrice(calculateActualPrice(book));

        // 2. 是否在库
        document.setInStock(book.getStock() != null && book.getStock() > 0);

        // 3. 热度分数（需要计算）
        document.setHotScore(calculateHotScore(book));

        // 4. 出版年份
        if (book.getPublishDate() != null) {
            document.setPublishYear(book.getPublishDate().getYear());
        }

        // 5. 综合搜索字段
        document.setCombinedSearch(buildCombinedSearch(book));

        // 6. 拼音字段（如果有拼音转换工具）
        // document.setTitlePinyin(PinyinUtil.toPinyin(book.getTitle()));
        // document.setAuthorPinyin(PinyinUtil.toPinyin(book.getAuthor()));

        return document;
    }

    /**
     * 批量转换
     */
    public List<BookDocument> convertToDocuments(List<BookDTO> books) {
        return books.stream()
                .map(this::convertToDocument)
                .collect(Collectors.toList());
    }

    /**
     * 设置标签（从BookTag列表转换）
     */


    /**
     * 设置分类路径
     */
    public void setCategoryPath(BookDocument document, List<String> categoryPath) {
        document.setCategoryPath(categoryPath);
    }

    /**
     * 计算实际价格
     */
    private BigDecimal calculateActualPrice(BookDTO book) {
        if (book.getDiscountPrice() != null
                && book.getDiscountPrice().compareTo(BigDecimal.ZERO) > 0
                && book.getDiscountPrice().compareTo(book.getPrice()) < 0) {
            return book.getDiscountPrice();
        }
        return book.getPrice();
    }

    /**
     * 计算热度分数
     */
    private Double calculateHotScore(BookDTO book) {
        double score = 0.0;

        // 销量权重
        if (book.getSoldCount() != null) {
            score += book.getSoldCount() * 2.0;
        }

        // 浏览量权重
        if (book.getViewCount() != null) {
            score += book.getViewCount() * 0.5;
        }

        // 评分权重
        if (book.getRating() != null) {
            score += book.getRating().doubleValue() * 100;
        }

        // 推荐加分
        if (book.getRecommended() != null && book.getRecommended()) {
            score += 500;
        }

        // 热门加分
        if (book.getHot() != null && book.getHot()) {
            score += 1000;
        }

        return score;
    }

    /**
     * 构建综合搜索字段
     */
    private String buildCombinedSearch(BookDTO book) {
        StringBuilder sb = new StringBuilder();

        if (book.getTitle() != null) sb.append(book.getTitle()).append(" ");
        if (book.getAuthor() != null) sb.append(book.getAuthor()).append(" ");
        if (book.getSubtitle() != null) sb.append(book.getSubtitle()).append(" ");
        if (book.getDescription() != null) sb.append(book.getDescription()).append(" ");
        if (book.getKeywords() != null) sb.append(book.getKeywords()).append(" ");
        if (book.getPublisher() != null) sb.append(book.getPublisher()).append(" ");
        if (book.getTranslator() != null) sb.append(book.getTranslator()).append(" ");

        return sb.toString().trim();
    }
}
