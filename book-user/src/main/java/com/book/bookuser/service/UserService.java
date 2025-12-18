package com.book.bookuser.service;

import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.exception.ServiceException;

import com.book.bookuser.entity.User;
import com.book.bookuser.mapper.UserMapper;
import com.book.bookuser.util.JwtUtil;
import com.book.bookuser.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;

    public void save(User user) {
        User check = userMapper.selectByUsername(user.getUsername());
        if (check != null) {
            throw new ServiceException("用户已经存在！", 1002);
        }
        String encode = PasswordUtil.encode(user.getPassword());
        user.setPassword(encode);
        int insert = userMapper.insert(user);
        if (insert != 1) {
            throw new ServiceException("注册失败", 1001);
        }
    }

    public String login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ServiceException("用户不存在！", 1003);
        }
        if(!PasswordUtil.matches(password,user.getPassword())){
            throw new ServiceException("用户名或密码错误！", 1004);
        }
        if (user.getStatus().equals(Constants.USER_STATUS_UNNORMAL)){
            throw new ServiceException("用户状态异常！", 1005);
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        return token;
    }

    public User getCurrentUserInfo() {
        Long currentUserId = UserContext.getCurrentUserId();
        User user = userMapper.selectById(currentUserId);
        return user;
    }
}
