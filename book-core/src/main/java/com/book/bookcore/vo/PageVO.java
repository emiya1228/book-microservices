package com.book.bookcore.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {

    private Integer page;
    private Integer size;
    private Long total;
    private Integer pages;
    private List<T> records;

    public PageVO() {}

    public PageVO(Integer page, Integer size, Long total, List<T> records) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.pages = (int) Math.ceil((double) total / size);
        this.records = records;
    }
}
