package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.book.bookcore.entity.BookCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookCategoryMapper extends BaseMapper<BookCategory> {

    /**
     * 查询所有分类（树形结构）
     */
    @Select("select id,parent_id,name,status,description from book_category order by parent_id, id")
    List<BookCategory> selectCategoryTree();

    /**
     * 根据父分类ID查询子分类
     */
    List<BookCategory> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 查询启用的分类列表
     */
    List<BookCategory> selectEnabledCategories();

    /**
     * 查询分类及其所有子分类ID
     */
    List<Long> selectCategoryAndChildrenIds(@Param("categoryId") Long categoryId);

    /**
     * 更新分类状态
     */
    @Update("update book_category set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}