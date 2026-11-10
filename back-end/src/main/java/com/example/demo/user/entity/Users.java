package com.example.demo.user.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUser;
	
	@Column(length = 255, nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	private String password;

	@Column(length = 50, name = "referral_code")
	private String referralCode;	
	
	public Users() {}
	public Users(long idUser) {setIdUser(idUser);}
	
	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String toString() {
		return email+"|"+name+"|"+password+"|"+referralCode;
	}
}