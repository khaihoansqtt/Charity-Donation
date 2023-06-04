package com.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.dto.UserDTO;
import com.repository.RoleRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
	
	public User(UserDTO userDto) {
		id = userDto.getId();
		userName = userDto.getUserName();
		password = userDto.getPassword();
		fullName = userDto.getFullName();
		email = userDto.getEmail();
		address = userDto.getAddress();
		phoneNumber = userDto.getPhoneNumber();
		note = userDto.getNote();
		status = userDto.getStatus();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<UserDonation> userDonation;
}
