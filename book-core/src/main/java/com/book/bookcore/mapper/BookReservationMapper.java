package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookcore.entity.BookReservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookReservationMapper extends BaseMapper<BookReservation> {

    /**
     * 插入图书预约记录
     */
    int insert(BookReservation reservation);

    /**
     * 根据ID查询预约记录
     */
    BookReservation selectById(@Param("id") Long id);

    /**
     * 更新预约记录
     */
    int update(BookReservation reservation);

    /**
     * 根据ID删除预约记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据用户ID和状态查询预约记录
     */
    List<BookReservation> selectByUserIdAndStatus(@Param("userId") Long userId,
                                                  @Param("status") Integer status);

    /**
     * 根据图书ID和状态查询预约记录
     */
    List<BookReservation> selectByBookIdAndStatus(@Param("bookId") Long bookId,
                                                  @Param("status") Integer status);

    /**
     * 查询用户对某本书的进行中预约（状态为0或1）
     */
    BookReservation selectActiveReservation(@Param("userId") Long userId,
                                            @Param("bookId") Long bookId);

    /**
     * 更新预约状态
     */
    int updateStatus(@Param("id") Long id,
                     @Param("status") Integer status,
                     @Param("updateTime") LocalDateTime updateTime);

    /**
     * 批量更新过期预约状态
     */
    int batchUpdateExpiredReservations(@Param("ids") List<Long> ids,
                                       @Param("status") Integer status,
                                       @Param("updateTime") LocalDateTime updateTime);

    /**
     * 查询所有预约记录（可分页）
     */
    List<BookReservation> selectAll();

    /**
     * 统计用户预约数量
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 查询等待中的预约（按预约时间排序）
     */
    List<BookReservation> selectWaitingReservations(@Param("bookId") Long bookId);

    /**
     * 查询等待中的预约（按预约时间排序）
     */
    @Select("select id from book_reservation where book_id = #{bookId} and status = 0 order by create_time limit 1")
    BookReservation selectWaitingReservation(@Param("bookId") Long bookId);

    @Select("select id from book_reservation where status = 1 order by update_time")
    List<BookReservation> selectReadyReservations();
    /**
     * 分页查询预约记录
     */
    List<BookReservation> selectPage(@Param("offset") int offset,
                                     @Param("pageSize") int pageSize,
                                     @Param("userId") Long userId,
                                     @Param("bookId") Long bookId,
                                     @Param("status") Integer status);
}
