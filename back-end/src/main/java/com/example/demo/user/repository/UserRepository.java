package com.example.demo.user.repository;

import com.example.demo.user.entity.Users;

import java.util.Optional;

public class UserRepository {
    private final UsersRepository usersRepository;

    UserRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users createUser(Users createUser) {
        return usersRepository.save(createUser);
    }

    public Optional<Users> getUserById(long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByCode(String code) {
        return usersRepository.findByReferralCode(code);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
