package com.example.demo.user;

import com.example.demo.shared.Security.JwtUtil;
import com.example.demo.shared.Security.SecurityConfig;
import com.example.demo.referrals.ReferralsServices;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.ResponseUserDTO;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.entity.Users;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UsersServices {
	
    private final UserRepository usersRepository;
    private final ReferralsServices referralsService;
    
    public UsersServices(UserRepository usersRepository,
                         ReferralsServices referralsService) {
        this.usersRepository = usersRepository;
        this.referralsService = referralsService;
    }
    
    @Transactional
    public ResponseUserDTO registerUser(CreateUserDTO userDTO, String ref) {
    	Users user = UserMapper.toEntity(userDTO);

    	if (this.usersRepository.getUserByEmail(user.getEmail()).isPresent()) {
    		throw new IllegalStateException("Email already registered!");
    	}
    	
    	user.setPassword(SecurityConfig.passwordEnconder().encode(user.getPassword()));
    	user.setReferralCode(createRef());
    	Users newUser = usersRepository.createUser(user);
    	
    	if (ref != null && !ref.isBlank()) {
            Users referrer = this.usersRepository.getUserByCode(ref)
                    .orElseThrow(() -> new RuntimeException("User not found."));

    		referralsService.createReferral(newUser, referrer);
    	}

        String accessToken = JwtUtil.generateToken(user.getEmail(), user.getIdUser());
    	return UserMapper.toResponse(newUser, referralsService.getReferralsPoints(newUser), accessToken);
    }

    //TODO MODIFICAR ESSE REF AQUI
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
        } while (this.usersRepository.getUserByCode(ref).isPresent());

        return ref;
    }

    public ResponseUserDTO loginUser(Users loginRequest) {
    	Users user = this.usersRepository.getUserByEmail(loginRequest.getEmail())
    			.orElseThrow(() -> new RuntimeException("User not found."));
    	
    	boolean matches = 
    			SecurityConfig.passwordEnconder().matches(loginRequest.getPassword(), user.getPassword());
    	
    	if (!matches) {
    		throw new RuntimeException("Incorrect password.");
    	}

        String accessToken = JwtUtil.generateToken(user.getEmail(), user.getIdUser());
        return UserMapper.toResponse(user, referralsService.getReferralsPoints(user), accessToken);
    }

    public ResponseUserDTO getUserById(long id) {
        Users user = this.usersRepository.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return  UserMapper.toResponse(user, referralsService.getReferralsPoints(user), null);
    }
}