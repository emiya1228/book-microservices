package com.book.bookuser.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user")  // 指定数据库表名
public class User {
    @TableId(type = IdType.AUTO)  // 主键，自增
    private Long id;

    private String username;

    private String password;

    @TableField("real_name")  // 数据库字段名是real_name，使用驼峰映射
    private String realName;

    private String email;

    private String phone;

    private String avatar;

    private Integer role;  // 0-读者，1-管理员

    private Integer status;  // 1-正常，0-禁用

    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField("register_time")
    private LocalDateTime registerTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
