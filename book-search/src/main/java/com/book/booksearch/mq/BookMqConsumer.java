package com.book.booksearch.mq;

import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.dto.BookDTO;
import com.book.bookcommon.exception.ServiceException;
import com.book.booksearch.entity.BookDocument;
import com.book.booksearch.utils.BookDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import com.book.booksearch.service.BookDocumentService;
import com.book.bookcommon.dto.BookMessage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@RocketMQMessageListener(
        topic = Constants.TOPIC_BOOK,
        consumerGroup = "book-consumer-group",
        selectorExpression = "BOOK_ADD || BOOK_UPDATE || BOOK_DELETE"  // 监听多个tag
)
@Slf4j
public class BookMqConsumer implements RocketMQListener<BookMessage> {

    @Resource
    private BookDocumentService bookSearchService;
    @Resource
    private BookDocumentConverter bookDocumentConverter;

    @PostConstruct
    public void init() {
        log.info("=== 消费者初始化信息 ===");
        RocketMQMessageListener annotation = this.getClass()
                .getAnnotation(RocketMQMessageListener.class);

        log.info("✅ Topic: {}", annotation.topic());
        log.info("✅ Consumer Group: {}", annotation.consumerGroup());
        log.info("✅ Selector Expression: {}", annotation.selectorExpression());
        log.info("✅ Message Model: {}", annotation.messageModel());
        log.info("✅ Consume Thread Max: {}", annotation.consumeThreadMax());
        log.info("=== 初始化完成 ===");
    }


    @Override
    public void onMessage(BookMessage message) {
        try {
            BookDTO data = message.getData();
            BookDocument bookDocument = bookDocumentConverter.convertToDocument(data);
            switch (message.getOperation()) {
                case "ADD":
                    bookSearchService.save(bookDocument);
                    break;

                case "UPDATE":
                    bookSearchService.update(bookDocument);
                    break;
                case "DELETE":
                    bookSearchService.delete(bookDocument);
                    break;
                default:
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
