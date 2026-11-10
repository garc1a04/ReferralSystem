package com.example.demo.user.dto;

import com.example.demo.user.entity.Users;

public class UserResponseDTO {
	
	private Long idUser;
	private String token;
	private String email;
	private String name;
	private String referralCode;
	private long points;
	
	public UserResponseDTO(Long idUser) {this.idUser = idUser;}
	
	public UserResponseDTO(Users user, String token, long points) {
		this.idUser = user.getIdUser();
		this.email = user.getEmail();
		this.name = user.getName();
		this.referralCode = user.getReferralCode();
		this.token = token;
		this.points = points;
	}

	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReferralCode() {
		return referralCode;
	}
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
}