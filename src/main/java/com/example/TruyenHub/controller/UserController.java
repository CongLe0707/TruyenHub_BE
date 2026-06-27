package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.LoginReq;
import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<CommonRes> registerUser(@Valid @RequestBody CommonReq<RegisterUserReq> req) {
        return ApiHandler.handle(req, userService::registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonRes> login(@Valid @RequestBody CommonReq<LoginReq> req) {
        return ApiHandler.handle(req, userService::login);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonRes> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return ApiHandler.handle(authorization, userService::logout);
    }
}
