package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.book.bookcore.entity.BookImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookImageMapper extends BaseMapper<BookImage> {

    /**
     * 根据图书ID查询图片列表
     */
    @Select("select * from book_image where book_id = #{bookId}")
    List<BookImage> selectByBookId(@Param("bookId") Long bookId);

    /**
     * 根据图书ID和图片类型查询
     */
    List<BookImage> selectByBookIdAndType(@Param("bookId") Long bookId,
                                          @Param("imageType") Integer imageType);

    /**
     * 根据图书ID删除图片
     */
    int deleteByBookId(@Param("bookId") Long bookId);

    /**
     * 批量插入图片
     */
    int batchInsert(@Param("list") List<BookImage> images);
}