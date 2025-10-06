package com.example.TruyenHub.mapper;

import com.example.TruyenHub.dto.req.RegisterUserReq;
import com.example.TruyenHub.model.entity.User;
import com.example.TruyenHub.model.enums.Role;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {Role.class, LocalDateTime.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "numberPhone", source = "numberPhone")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(req.password()))")
    @Mapping(target = "role", expression = "java(Role.user)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    User toEntity(RegisterUserReq req, @Context PasswordEncoder passwordEncoder);
}
