package com.example.demo.user.mapper;

import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.entity.Users;

public class UserMapper {
    public static Users toEntity(CreateUserDTO dto) {
        Users user = new Users();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());
        return user;
    }
}
