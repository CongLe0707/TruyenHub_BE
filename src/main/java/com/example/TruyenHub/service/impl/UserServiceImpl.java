package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.dto.res.RegisterUserRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.mapper.UserMapper;
import com.example.TruyenHub.model.entity.User;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.outfras.repo.UserRepository;
import com.example.TruyenHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public RegisterUserRes registerUser(CommonReq<RegisterUserReq> req) {
        RegisterUserReq data = req.getData();

        if (userRepository.findByUserName(data.userName()).isPresent()) {
            throw new DelegationServiceException(
                    ResultCode.USER_NAME.getCode(),
                    ResultCode.USER_NAME.getMessage()
            );
        }

        User user = userRepository.save(userMapper.toEntity(data, passwordEncoder));

        return new RegisterUserRes (
                user.getUserName(),
                user.getNumberPhone(),
                user.getEmail()
        );
    }
}
