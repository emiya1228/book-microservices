package com.book.booksearch.repository;

import com.book.booksearch.entity.BookDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookDocumentRepository  extends ElasticsearchRepository<BookDocument, String> {

    public BookDocument findByTitle(String title);

    public List<BookDocument> findByTitleLike(String title);

    public List<BookDocument> findByAuthor(String author);

    public List<BookDocument> findByAuthorLike(String author);

    public List<BookDocument> findByPublisherContaining(String title);

    public List<BookDocument> findAllById(List<Long> ids);
}
