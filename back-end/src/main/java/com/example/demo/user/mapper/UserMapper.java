package com.example.demo.user.mapper;

import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.ResponseTokenDTO;
import com.example.demo.user.dto.ResponseUserDTO;
import com.example.demo.user.entity.Users;

public class UserMapper {
    public static Users toEntity(CreateUserDTO dto) {
        Users user = new Users();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());
        return user;
    }

    public static ResponseUserDTO toResponse(Users user, Long points, String acessToken) {
        ResponseTokenDTO token = null;

        if(acessToken != null) token = new ResponseTokenDTO(acessToken);


        ResponseUserDTO response = new ResponseUserDTO(
                user.getIdUser(),
                user.getReferralCode(),
                user.getEmail(),
                user.getName(),
                points,
                token);

        return response;
    }
}
