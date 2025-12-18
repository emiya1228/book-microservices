package com.book.bookcommon.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BookMessage implements Serializable {
    private String operation;    // ADD, UPDATE, DELETE
    private Long bookId;         // 书籍ID
    private Long timestamp;      // 时间戳
    private BookDTO data;         // 可选：传递完整数据

    public BookMessage(String operation, Long bookId, BookDTO data) {
        this.operation = operation;
        this.bookId = bookId;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }
}