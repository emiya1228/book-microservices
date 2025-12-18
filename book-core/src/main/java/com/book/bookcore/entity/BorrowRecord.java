package com.book.bookcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("borrow_records")
public class BorrowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("book_id")
    private Long bookId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("borrow_date")
    private LocalDate borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("due_date")
    private LocalDate dueDate;

    @TableField("return_date")
    private LocalDate returnDate;

    @TableField("status")
    private String status; // 借阅中, 已归还, 逾期

    @TableField("renew_count")
    private Integer renewCount;


    // 非数据库字段
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String bookTitle;

    @TableField(exist = false)
    private String bookIsbn;

    @TableField(exist = false)
    private Integer overdueDays; // 逾期天数

    @TableField(exist = false)
    private Boolean isOverdue; // 是否逾期
}
