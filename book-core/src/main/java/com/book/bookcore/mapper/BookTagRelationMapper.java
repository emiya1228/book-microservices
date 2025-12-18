package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.book.bookcore.entity.BookTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookTagRelationMapper extends BaseMapper<BookTagRelation> {

    /**
     * 批量插入图书-标签关联
     */
    int batchInsert(@Param("relations") List<BookTagRelation> relations);

    /**
     * 根据图书ID删除关联
     */
    int deleteByBookId(@Param("bookId") Long bookId);

    /**
     * 根据标签ID删除关联
     */
    int deleteByTagId(@Param("tagId") Long tagId);

    /**
     * 根据图书ID查询关联的标签ID列表
     */
    @Select("select tag_id from book_tag_relation where book_id = #{bookId}")
    List<Long> selectTagIdsByBookId(@Param("bookId") Long bookId);

    /**
     * 根据标签ID查询关联的图书ID列表
     */
    List<Long> selectBookIdsByTagId(@Param("tagId") Long tagId);
}
