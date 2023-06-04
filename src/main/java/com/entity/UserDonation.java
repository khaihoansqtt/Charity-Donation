package com.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users_donations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDonation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "money")
	private int money;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "text")
	private String text;
	
	@Column(name = "created_at")
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "donation_id")
	private Donation donation;
}
