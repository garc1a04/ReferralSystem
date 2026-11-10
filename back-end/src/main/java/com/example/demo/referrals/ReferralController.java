package com.example.demo.referrals;

import com.example.demo.user.UsersServices;
import com.example.demo.user.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/ref")
public class ReferralController {
	
	private UsersServices usersService;
	private ReferralsServices referralsService;
	
	public ReferralController(@Autowired UsersServices usersService,
							  @Autowired ReferralsServices referralsService) {
	    this.usersService = usersService;
	    this.referralsService = referralsService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity getUsersReferr(@PathVariable Long id) {
        try {
        	Users user = new Users();
        	user.setIdUser(id);  	
        	return new ResponseEntity<>(referralsService.getReferralsByUser(user), HttpStatus.OK);
        } catch (Exception err) {
        	return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
