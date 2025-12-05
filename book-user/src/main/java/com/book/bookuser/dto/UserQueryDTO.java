package com.book.bookuser.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserQueryDTO {
    private String username;
    private String realName;
    private String email;
    private String phone;
    private Integer role;
    private Integer status;
    private LocalDateTime registerTimeStart;
    private LocalDateTime registerTimeEnd;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
