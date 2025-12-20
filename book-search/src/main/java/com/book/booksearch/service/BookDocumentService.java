package com.book.booksearch.service;

import com.book.bookcommon.exception.ServiceException;
import com.book.booksearch.entity.BookDocument;
import com.book.booksearch.repository.BookDocumentRepository;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookDocumentService {
    @Resource
    private BookDocumentRepository bookDocumentRepository;

    public BookDocument getBookDocumentById(Long id) {
        Optional<BookDocument> byId = bookDocumentRepository.findById(String.valueOf(id));
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new ServiceException("查询无返回记录");
        }
    }

    public List<BookDocument> getBookDocumentsByTitle(String title) {
        try {
            List<BookDocument> bookDocuments = bookDocumentRepository.findByTitleLike(title);
            return bookDocuments;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<BookDocument> getBookDocumentsByAuthor(String author) {
        try {
            List<BookDocument> bookDocuments = bookDocumentRepository.findByAuthorLike(author);
            return bookDocuments;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<BookDocument> getDocumentsByPublisher(String publisher) {
        try {
            List<BookDocument> bookDocuments = bookDocumentRepository.findByPublisherContaining(publisher);
            return bookDocuments;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void save(BookDocument bookDocument) {
        bookDocumentRepository.save(bookDocument);
    }

    public void delete(BookDocument bookDocument) {
        bookDocumentRepository.delete(bookDocument);
    }

    public void update(BookDocument bookDocument) {
        bookDocumentRepository.save(bookDocument);
    }

    public void updateBatch(List<BookDocument> bookDocuments) {
        bookDocumentRepository.saveAll(bookDocuments);
    }


}
