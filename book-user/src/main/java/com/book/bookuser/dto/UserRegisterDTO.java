package com.book.bookuser.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名4-20位字母数字下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码需字母+数字，至少8位")
    private String password;

    private String realName;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @NotNull(message = "角色不能为空")
    @Min(value = 0, message = "角色值无效")
    @Max(value = 1, message = "角色值无效")
    private Integer role = 0; // 默认读者
}
