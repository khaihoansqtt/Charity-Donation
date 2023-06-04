 package com.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dto.DonationDTO;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="donations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Donation {
	
	public Donation(DonationDTO donationDTO) {
		code = donationDTO.getCode();
		name = donationDTO.getName();
		description = donationDTO.getDescription();
		money = donationDTO.getMoney();
		startDate = donationDTO.getStartDate();
		endDate = donationDTO.getEndDate();
		status = donationDTO.getStatus();
		organizationName = donationDTO.getOrganizationName();
		phoneNumber = donationDTO.getPhoneNumber();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "money")
	private int money;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "organization_name")
	private String organizationName;
	
	@Column(name = "phone_number ")
	private String phoneNumber ;
	
	@OneToMany(mappedBy = "donation", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<UserDonation> userDonation;
}
