package com.book.bookcore.mq;

import com.book.bookcommon.dto.BookMessage;
import com.book.bookcore.entity.Book;
import com.book.bookcommon.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.book.bookcommon.constant.Constants;
import javax.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@Service
@Slf4j
public class BookMqProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送书籍新增消息
     */
    public void sendBookAddMessage(Long bookId, Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        BookMessage message = new BookMessage("ADD", bookId, bookDTO);
        sendMessage(Constants.TAG_BOOK_ADD, message);
        log.info("发送书籍新增消息，bookId: {}", bookId);
    }

    /**
     * 发送书籍更新消息
     */
    public void sendBookUpdateMessage(Long bookId, Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        BookMessage message = new BookMessage("UPDATE", bookId, bookDTO);
        sendMessage(Constants.TAG_BOOK_UPDATE, message);
        log.info("发送书籍更新消息，bookId: {}", bookId);
    }

    /**
     * 发送书籍删除消息
     */
    public void sendBookDeleteMessage(Long bookId, Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        BookMessage message = new BookMessage("DELETE", bookId, bookDTO);
        sendMessage(Constants.TAG_BOOK_DELETE, message);
        log.info("发送书籍删除消息，bookId: {}", bookId);
    }

    private void sendMessage(String tag, BookMessage message) {
        String destination = String.format("%s:%s",
                Constants.TOPIC_BOOK, tag);
        rocketMQTemplate.convertAndSend(destination, message);
    }
}
