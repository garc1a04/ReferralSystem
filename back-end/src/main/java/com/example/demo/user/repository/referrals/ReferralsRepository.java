package com.example.demo.user.repository.referrals;

import java.util.List;

import com.example.demo.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.entity.Referrals;

public interface ReferralsRepository extends JpaRepository<Referrals, Long>{
	List<Referrals> findByReferrerIdUser(Long id);
}