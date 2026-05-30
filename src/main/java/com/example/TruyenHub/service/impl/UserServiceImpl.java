package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.LoginReq;
import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.dto.res.LoginRes;
import com.example.TruyenHub.dto.res.RegisterUserRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.mapper.UserMapper;
import com.example.TruyenHub.model.entity.RefreshToken;
import com.example.TruyenHub.model.entity.User;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.outfras.repo.UserRepository;
import com.example.TruyenHub.outfras.repo.RefreshTokenRepository;
import com.example.TruyenHub.service.UserService;
import com.example.TruyenHub.utils.Constants;
import com.example.TruyenHub.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;


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
                user.getEmail(),
                user.getNumberPhone()
        );
    }

    @Transactional
    @Override
    public LoginRes login(CommonReq<LoginReq> req) {
        LoginReq data = req.getData();

        User user = userRepository.findByUserName(data.userName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.USER_NOT_FOUND.getCode(),
                        ResultCode.USER_NOT_FOUND.getMessage()
                ));

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new DelegationServiceException(
                    ResultCode.USER_NOT_FOUND.getCode(),
                    ResultCode.USER_NOT_FOUND.getMessage()
            );
        }
        RefreshToken refreshToken = createAndSaveRefreshToken(user);

        return new LoginRes(
                JwtUtils.generateAccessToken(user.getUserName(), Constants.ACCESS_TOKEN_TTL_SECONDS),
                refreshToken.getToken(),
                user.getRole().name()
        );
    }


    private RefreshToken createAndSaveRefreshToken(User user) {
        String refreshTokenStr = JwtUtils.generateRefreshToken(user.getUserName(), Constants.REFRESH_TOKEN_TTL_SECONDS);

        refreshTokenRepository.deleteAllByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenStr);
        refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(Constants.REFRESH_TOKEN_TTL_SECONDS));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

}
