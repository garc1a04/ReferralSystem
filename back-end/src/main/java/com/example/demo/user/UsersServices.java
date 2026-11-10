package com.example.demo.user;

import com.example.demo.shared.Security.JwtUtil;
import com.example.demo.shared.Security.SecurityConfig;
import com.example.demo.referrals.ReferralsServices;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.entity.Users;
import com.example.demo.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UsersServices {
	
    private final UsersRepository usersRepository;
    private final ReferralsServices referralsService;
    
    public UsersServices(UsersRepository usersRepository,
                         ReferralsServices referralsService) {
        this.usersRepository = usersRepository;
        this.referralsService = referralsService;
    }
    
    @Transactional
    public Users registerUser(Users user, String ref) {
    	
    	if (getUserByEmail(user.getEmail()).isPresent()) {
    		throw new IllegalStateException("Email already registered!");
    	}
    	
    	user.setPassword(SecurityConfig.passwordEnconder().encode(user.getPassword()));
    	user.setReferralCode(createRef());
    	Users newUser = usersRepository.save(user);
    	
    	if (ref != null && !ref.isBlank()) {
    		referralsService.createReferral(newUser, getUserByCode(ref).get());
    	}
    	
    	return newUser;
    }
    
    public String createRef() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        String ref;

        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            ref = sb.toString();
        } while (getUserByCode(ref).isPresent());

        return ref;
    }
    
    public UserResponseDTO loginUser(Users loginRequest) {
    	Users user = usersRepository.findByEmail(loginRequest.getEmail())
    			.orElseThrow(() -> new RuntimeException("User not found."));
    	
    	boolean matches = 
    			SecurityConfig.passwordEnconder().matches(loginRequest.getPassword(), user.getPassword());
    	
    	if (!matches) {
    		throw new RuntimeException("Incorrect password.");
    	}
    	
    	UserResponseDTO response = new UserResponseDTO(user,JwtUtil.generateToken(user.getEmail(), user.getIdUser()),referralsService.getReferralsPoints(user));
    	return response;
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