package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.book.bookcore.entity.BookTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookTagMapper extends BaseMapper<BookTag> {

    /**
     * 根据图书ID查询标签
     */
    List<BookTag> selectTagsByBookId(@Param("bookId") Long bookId);


    /**
     * 根据标签名称查询
     */
    BookTag selectByName(@Param("name") String name);
}