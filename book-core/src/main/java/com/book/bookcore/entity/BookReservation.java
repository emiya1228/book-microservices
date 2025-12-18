package com.book.bookcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@TableName("book_reservation")
public class BookReservation {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    @NotNull
    private Long bookId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reserveDate;

    private Integer status; // 0-等待中，1-可借阅，2-已借阅，3-已取消，4-已过期


    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 非数据库字段
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String bookTitle;

    @TableField(exist = false)
    private String bookIsbn;

    @TableField(exist = false)
    private Integer queuePosition; // 排队位置
}