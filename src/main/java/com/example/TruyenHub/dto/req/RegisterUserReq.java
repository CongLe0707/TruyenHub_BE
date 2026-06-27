package com.example.TruyenHub.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserReq(
      @NotBlank(message = "Tên đăng nhập không được để trống")
      @Size(min = 3, max = 30, message = "Tên đăng nhập phải từ 3 đến 30 ký tự")
      @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập không được chứa ký tự đặc biệt hoặc khoảng trắng")
      String userName,

      @NotBlank(message = "Email không được để trống")
      @Size(max = 50, message = "Email không được quá 50 ký tự")
      @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email không hợp lệ. Vui lòng sử dụng đuôi @gmail.com")
      String email,

      @NotBlank(message = "Số điện thoại không được để trống")
      @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải bao gồm đúng 10 chữ số")
      String numberPhone,

      @NotBlank(message = "Mật khẩu không được để trống")
      @Size(min = 6, max = 50, message = "Mật khẩu phải từ 6 đến 50 ký tự")
      String password
) {
}
