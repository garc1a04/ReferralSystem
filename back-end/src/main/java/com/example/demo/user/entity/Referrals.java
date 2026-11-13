package com.example.demo.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Referrals")
public class Referrals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReferals")
	private long idReferals;
	
	@ManyToOne
	@JoinColumn(name = "User_idUser", nullable = false)
	private Users userReferred;
	
	@ManyToOne
	@JoinColumn(name = "referrer_idUser", nullable = false)
	private Users referrer;
	
	@Column(name = "Date")
	private LocalDateTime Date;

	public long getIdReferals() {
		return idReferals;
	}

	public void setIdReferals(long idReferals) {
		this.idReferals = idReferals;
	}

	public Users getUserReferred() {
		return userReferred;
	}

	public void setUserReferred(Users userReferred) {
		this.userReferred = userReferred;
	}

	public Users getReferrer() {
		return referrer;
	}

	public void setReferrer(Users referrer) {
		this.referrer = referrer;
	}

	public LocalDateTime getDate() {
		return Date;
	}

	public void setDate(LocalDateTime date) {
		Date = date;
	}
	
	
}