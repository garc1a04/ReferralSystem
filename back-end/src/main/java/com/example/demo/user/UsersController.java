package com.example.demo.user;

import com.example.demo.referrals.ReferralsServices;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UsersController {
	
    private UsersServices usersService;
    private ReferralsServices referralsService;

    public UsersController(@Autowired UsersServices usersService, @Autowired ReferralsServices referralsService) {
        this.usersService = usersService;
        this.referralsService = referralsService;
    }
    
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Users user, @RequestParam(required = false) String ref) {
		try {
			Users response = usersService.registerUser(user, ref);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException err) {
			return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch (Exception err) {
			return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Users user) {
    	try {
    		UserResponseDTO loggedUser = usersService.loginUser(user);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        } catch (Exception err) {
        	return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/{id}")
    public ResponseEntity getUser(@PathVariable long id) {
        try {
        	Users user = usersService.getUserById(id).get();
        	return new ResponseEntity<>(new UserResponseDTO(user,"",referralsService.getReferralsPoints(user)), HttpStatus.OK);
        } catch (Exception err) {
        	return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}