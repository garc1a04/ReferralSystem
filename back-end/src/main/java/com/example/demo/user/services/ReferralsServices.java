package com.example.demo.user.services;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.user.entity.Users;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.Referrals;
import com.example.demo.user.repository.referrals.ReferralsRepository;

@Service
public class ReferralsServices {
    private final ReferralsRepository referralsRepository;

    public ReferralsServices(ReferralsRepository referralsRepository) {
        this.referralsRepository = referralsRepository;
    }

    public Referrals buildReferral(Users userReferred, Users referrer) {
    	if (userReferred == null || referrer == null) {
    	    throw new IllegalArgumentException("User referred and referrer must not be null");
    	}
    	
    	if (userReferred.getIdUser() == referrer.getIdUser()) {
    	    throw new IllegalArgumentException("A user cannot refer themselves");
    	}
    	
    	Referrals aux = new Referrals();
	    aux.setUserReferred(userReferred);
	    aux.setReferrer(referrer);
	    aux.setDate(LocalDateTime.now());
	    return aux;
    }
    
    public Referrals createReferral(Users userReferred, Users referrer) {    	
        return referralsRepository.save(buildReferral(userReferred, referrer));
    }
    
    public List<Referrals> getReferralsByUser(Long id) {
        return referralsRepository.findByReferrerIdUser(id);
    }

    public long getReferralsPoints(Long id) {
        return referralsRepository.findByReferrerIdUser(id).size();
    }
}