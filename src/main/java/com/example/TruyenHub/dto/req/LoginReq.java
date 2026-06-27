package com.example.TruyenHub.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReq (
        @NotBlank(message = "Tên đăng nhập không được để trống")
        @Size(max = 30, message = "Tên đăng nhập không hợp lệ")
        String userName,
        
        @NotBlank(message = "Mật khẩu không được để trống")
        String password
) {
}
