package com.dto;

import java.util.Date;
import java.util.List;

import com.entity.User;
import com.entity.UserDonation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
	
	public UserDTO(User user) {
		id = user.getId();
		userName = user.getUserName();
		password = user.getPassword();
		fullName = user.getFullName();
		email = user.getEmail();
		address = user.getAddress();
		phoneNumber = user.getPhoneNumber();
		note = user.getNote();
		status = user.getStatus();
		roleId = user.getRole().getId();
		roleName = user.getRole().getRoleName();
		createdAt = user.getCreatedAt();
		userDonations = user.getUserDonation();	
	}
	private int id;
	private String userName;
	private String password;
	private String fullName;
	private String email;
	private String address;
	private String phoneNumber;
	private String note;
	private int status;
	private int roleId;
	private String roleName;
	private Date createdAt;
	private List<UserDonation> userDonations;
}
