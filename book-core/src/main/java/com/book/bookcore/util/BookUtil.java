package com.book.bookcore.util;

import com.book.bookcore.dto.BookCreateDTO;
import com.book.bookcore.dto.BookUpdateDTO;
import com.book.bookcore.entity.Book;
import com.book.bookcore.entity.BookImage;
import com.book.bookcore.entity.BookTagRelation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookUtil {

    public static Book createDTO2Book (BookCreateDTO bookCreateDTO) {
        Book book = new Book();
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setTitle(bookCreateDTO.getTitle());
        book.setIsbn(bookCreateDTO.getIsbn());
        book.setPrice(bookCreateDTO.getPrice());
        book.setPublisher(bookCreateDTO.getPublisher());
        book.setPublishDate(bookCreateDTO.getPublishDate());
        book.setSubtitle(bookCreateDTO.getSubtitle());
        book.setDescription(bookCreateDTO.getDescription());
        book.setLanguage(bookCreateDTO.getLanguage());
        book.setPageCount(bookCreateDTO.getPageCount());
        book.setCoverImage(bookCreateDTO.getCoverImage());
        book.setDiscountPrice(bookCreateDTO.getDiscountPrice());
        book.setStock(bookCreateDTO.getStock());
        book.setCategoryId(bookCreateDTO.getCategoryId());
        book.setKeywords(bookCreateDTO.getKeywords());
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        return book;
    }

    public static List<BookTagRelation> tagIds2BookTagRelations (List<Long> tagIds, Long bookId) {
        List<BookTagRelation> bookTagRelations = new ArrayList<>();
        for (Long tagId : tagIds) {
            BookTagRelation bookTagRelation = new BookTagRelation();
            bookTagRelation.setBookId(bookId);
            bookTagRelation.setTagId(tagId);
            bookTagRelations.add(bookTagRelation);
        }
        return bookTagRelations;
    }

    public static List<BookImage> images2BookImages (List<BookCreateDTO.BookImageDTO> images, Long bookId) {
        List<BookImage> bookImages = new ArrayList<>();
        for (BookCreateDTO.BookImageDTO tagId : images) {
            BookImage bookTagRelation = new BookImage();
            bookTagRelation.setBookId(bookId);
            bookTagRelation.setImageType(tagId.getImageType());
            bookTagRelation.setImageUrl(tagId.getImageUrl());
            bookTagRelation.setSortOrder(tagId.getSortOrder());
            bookImages.add(bookTagRelation);
        }
        return bookImages;
    }

    public static List<BookImage> imagesup2BookImages (List<BookUpdateDTO.BookImageDTO> images, Long bookId) {
        List<BookImage> bookImages = new ArrayList<>();
        for (BookUpdateDTO.BookImageDTO tagId : images) {
            BookImage bookTagRelation = new BookImage();
            bookTagRelation.setBookId(bookId);
            bookTagRelation.setImageType(tagId.getImageType());
            bookTagRelation.setImageUrl(tagId.getImageUrl());
            bookTagRelation.setSortOrder(tagId.getSortOrder());
            bookImages.add(bookTagRelation);
        }
        return bookImages;
    }

    public static Book updateDTO2Book (BookUpdateDTO bookCreateDTO) {
        Book book = new Book();
        book.setId(bookCreateDTO.getBookId());
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setTitle(bookCreateDTO.getTitle());
        book.setIsbn(bookCreateDTO.getIsbn());
        book.setPrice(bookCreateDTO.getPrice());
        book.setPublisher(bookCreateDTO.getPublisher());
        book.setPublishDate(bookCreateDTO.getPublishDate());
        book.setSubtitle(bookCreateDTO.getSubtitle());
        book.setDescription(bookCreateDTO.getDescription());
        book.setLanguage(bookCreateDTO.getLanguage());
        book.setPageCount(bookCreateDTO.getPageCount());
        book.setCoverImage(bookCreateDTO.getCoverImage());
        book.setDiscountPrice(bookCreateDTO.getDiscountPrice());
        book.setStock(bookCreateDTO.getStock());
        book.setCategoryId(bookCreateDTO.getCategoryId());
        book.setKeywords(bookCreateDTO.getKeywords());
        book.setHot(bookCreateDTO.getHot());
        book.setUpdateTime(LocalDateTime.now());
        book.setRecommended(bookCreateDTO.getRecommended());
        return book;
    }
}
