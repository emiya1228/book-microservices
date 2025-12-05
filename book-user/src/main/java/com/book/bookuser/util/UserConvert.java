package com.book.bookuser.util;

import com.book.bookuser.dto.UserLoginDTO;
import com.book.bookuser.dto.UserQueryDTO;
import com.book.bookuser.dto.UserRegisterDTO;
import com.book.bookuser.entity.User;
import com.book.bookuser.vo.UserVO;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {

    public static User toEntity (UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPhone(userRegisterDTO.getPhone());
        user.setRole(userRegisterDTO.getRole());
        user.setRealName(userRegisterDTO.getRealName());
        return user;
    }

    public static User toEntity (UserLoginDTO userLoginDTO) {
        User user = new User();
        user.setUsername(userLoginDTO.getUsername());
        user.setPassword(userLoginDTO.getPassword());
        return user;
    }

    public static UserVO toVO (User user) {
        UserVO userVO = new UserVO();
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setRole(user.getRole());
        userVO.setRealName(user.getRealName());
        userVO.setId(user.getId());
        userVO.setAvatar(user.getAvatar());
        userVO.setStatus(user.getStatus());
        userVO.setRegisterTime(user.getRegisterTime());
        return userVO;
    }

}
