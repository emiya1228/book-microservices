package com.book.bookcore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookcore.entity.BookTag;
import com.book.bookcore.mapper.BookTagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookTagService {
    @Resource
    private BookTagMapper bookTagMapper;

    @Transactional(rollbackFor = Exception.class)
    public BookTag getOrCreateTag(String name, String color) {
        QueryWrapper<BookTag> eq = new QueryWrapper<BookTag>().eq("name", name);
        BookTag bookTag = bookTagMapper.selectOne(eq);
        if (bookTag == null) {
            bookTag = new BookTag();
            bookTag.setName(name);
            bookTag.setColor(color);
            bookTag.setCreateTime(LocalDateTime.now());
            bookTagMapper.insert(bookTag);
        }
        return bookTag;
    }

    public List<BookTag> getAllTags() {
        return bookTagMapper.selectList(null);
    }
}
