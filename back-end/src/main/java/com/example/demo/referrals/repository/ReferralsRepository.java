package com.example.demo.referrals.repository;

import java.util.List;

import com.example.demo.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.referrals.entity.Referrals;

public interface ReferralsRepository extends JpaRepository<Referrals, Long>{
	List<Referrals> findByReferrer(Users referrer);
}