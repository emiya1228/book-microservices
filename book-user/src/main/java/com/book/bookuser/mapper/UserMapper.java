package com.book.bookuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.book.bookuser.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    // 根据用户名查询（唯一索引，常用）
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);

    // 根据邮箱查询
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(@Param("email") String email);

    // 统计今日注册用户数
    @Select("SELECT COUNT(*) FROM user WHERE DATE(register_time) = CURDATE()")
    Integer countTodayRegister();

    // 查询活跃用户（最近7天登录过）
    @Select("SELECT * FROM user WHERE last_login_time >= #{date}")
    List<User> selectActiveUsers(@Param("date") LocalDateTime date);


}