package com.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.entity.Donation;
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
public class DonationDTO {
	
	public DonationDTO(Donation donation) {
		id = donation.getId();
		code = donation.getCode();
		name = donation.getName();
		description = donation.getDescription();
		money = donation.getMoney();
		startDate = donation.getStartDate();
		endDate = donation.getEndDate();
		status = donation.getStatus();
		organizationName = donation.getOrganizationName();
		phoneNumber = donation.getPhoneNumber();
		userDonations = donation.getUserDonation();
	}
	
	private int id;
	private String code;
	private String name;
	private String description;
	private int money;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	private int status;
	private String organizationName;
	private String phoneNumber;
	private List<UserDonation> userDonations;
}
