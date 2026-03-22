package com.book.bookuser.controller;

import com.book.bookcommon.result.Response;
import com.book.bookuser.dto.UserLoginDTO;
import com.book.bookuser.service.UserService;
import com.book.bookuser.util.UserConvert;
import com.book.bookuser.vo.UserVO;
import org.springframework.web.bind.annotation.*;
import com.book.bookuser.dto.UserRegisterDTO;
import com.book.bookuser.entity.User;

import javax.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Response<UserVO> register(@Valid @RequestBody UserRegisterDTO dto) {
        // 1. DTO -> Entity
        User user = UserConvert.toEntity(dto);

        // 2. 业务处理
        userService.save(user);

        // 3. Entity -> VO
        UserVO vo = UserConvert.toVO(user);

        return Response.success(vo);
    }

    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody UserLoginDTO dto) {
        // 1. DTO -> Entity
        User user = UserConvert.toEntity(dto);

        // 2. 业务处理
        String token = userService.login(user.getUsername(), user.getPassword());

        // 3. Entity -> VO
        UserVO vo = UserConvert.toVO(user);

        return Response.success(token);
    }

    @GetMapping("/info")
    public Response<UserVO> getUser() {
        User currentUserInfo = userService.getCurrentUserInfo();
        UserVO vo = UserConvert.toVO(currentUserInfo);
        return Response.success(vo);
    }
}
