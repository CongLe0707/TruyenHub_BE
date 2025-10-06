package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.dto.req.LoginReq;
import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.RedisBlacklistService;
import com.example.TruyenHub.service.UserService;
import com.example.TruyenHub.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final RedisBlacklistService redisBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<CommonRes> registerUser(@RequestBody CommonReq<RegisterUserReq> req) {
        return ApiHandler.handle(req, userService::registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonRes> login(@RequestBody CommonReq<LoginReq> req) {
        return ApiHandler.handle(req, userService::login);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonRes> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return ApiHandler.handle(null, r -> {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                redisBlacklistService.blacklistToken(token, Constants.ACCESS_TOKEN_TTL_SECONDS);
            }
            return "Đăng xuất thành công";
        });
    }
}
