package com.book.bookcore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookcore.entity.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
}
