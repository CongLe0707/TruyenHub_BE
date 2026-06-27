package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.LoginReq;
import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.dto.res.LoginRes;
import com.example.TruyenHub.dto.res.RegisterUserRes;

public interface UserService {

    RegisterUserRes registerUser(CommonReq<RegisterUserReq> req);

    LoginRes login(CommonReq<LoginReq> req);
    String logout(String authorization);

}
