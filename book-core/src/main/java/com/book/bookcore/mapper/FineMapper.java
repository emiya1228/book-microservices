package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookcore.entity.Fine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FineMapper extends BaseMapper<Fine> {

    // 插入罚金记录
    int insert(Fine fine);

    // 根据ID查询
    Fine selectById(Long id);

    // 根据用户ID查询
    List<Fine> selectByUserId(Long userId);

    // 根据借阅记录ID查询
    List<Fine> selectByBorrowRecordId(Long borrowRecordId);

    // 查询用户未支付的罚金
    List<Fine> selectUnpaidByUserId(Long userId);

    // 更新罚金状态为已支付
    int updateToPaid(@Param("id") Long id, @Param("payTime") LocalDateTime payTime);

    // 统计用户总罚金金额
    BigDecimal sumAmountByUserId(Long userId);

    // 统计用户未支付罚金总额
    BigDecimal sumUnpaidAmountByUserId(Long userId);

    // 删除罚金记录（按需）
    int deleteById(Long id);
}
