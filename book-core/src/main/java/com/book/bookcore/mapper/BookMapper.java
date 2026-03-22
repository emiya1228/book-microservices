package com.book.bookcore.mapper;

import com.book.bookcore.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    /**
     * 根据分类ID查询图书列表
     */
    List<Book> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据分类ID查询图书列表
     */
    @Select("select * from book b join book_category bc on b.category_id = bc.id where bc.name = #{name} and bc.status = 1")
    List<Book> selectByCategory(@Param("name") String name);
    /**
     * 根据书名查询图书列表
     */
    List<Book> selectByTitle(@Param("title") String title);

    /**
     * 根据作者查询图书
     */
    List<Book> selectByAuthor(@Param("author") String author);

    /**
     * 根据出版社查询图书
     */
    List<Book> selectByPublisher(@Param("publisher") String publisher);

    /**
     * 根据状态查询图书
     */
    List<Book> selectByStatus(@Param("status") Integer status);

    /**
     * 搜索图书（多条件）
     */
    //IPage<Book> searchBooks(Page<Book> page, @Param("query") BookSearchVO searchVO);

    /**
     * 获取热门图书
     */
    List<Book> selectHotBooks(@Param("limit") Integer limit);

    /**
     * 获取推荐图书
     */
    List<Book> selectRecommendedBooks(@Param("limit") Integer limit);

    /**
     * 根据价格范围查询
     */
    List<Book> selectByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice);

    /**
     * 增加图书销量
     */
    int increaseSoldCount(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 增加图书浏览量
     */
    int increaseViewCount(@Param("id") Long id);

    /**
     * 减少库存
     */
    @Update("update book set stock = stock - #{count} where id = #{id} and stock >= #{count}")
    int decreaseStock(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 增加库存
     */
    @Update("update book set stock = stock + #{count} where id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("count") Integer count);

    @Select("select a.category_id  from book a join borrow_records b on a.id = b.book_id where b.user_id = #{userId} group by a.category_id order by count(1) desc limit 3")
    List<String> getCategoryIdsByUserId(@Param("userId") Long userId);

    List<Book> getRecommendBooks(List<String> categories, Long userId);

    /**
     * 根据ISBN查询图书
     */
    Book selectByIsbn(@Param("isbn") String isbn);

    /**
     * 批量查询图书
     */
    List<Book> selectBatchIds(@Param("ids") List<Long> ids);


    List<Book> getBooksToday(@Param("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,@Param("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end);
}