package com.example.TruyenHub.dto.req;


public record RegisterUserReq(
      String userName,
      String email,
      String numberPhone,
      String password
) {
}
