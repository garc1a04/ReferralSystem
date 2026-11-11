package com.example.demo.user.dto;

public record ResponseUserDTO(Long idUser, String code, String email, String name, Long points, ResponseTokenDTO token) { }
