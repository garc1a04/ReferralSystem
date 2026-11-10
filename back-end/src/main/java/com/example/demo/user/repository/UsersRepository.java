package com.example.demo.user.repository;

import com.example.demo.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findById(Long id);
	Optional<Users> findByEmailAndPassword(String email, String password);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByReferralCode(String referralCode);
}