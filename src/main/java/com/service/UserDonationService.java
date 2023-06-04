package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Donation;
import com.entity.User;
import com.entity.UserDonation;
import com.repository.DonationRepository;
import com.repository.UserDonationRepository;
import com.repository.UserRepository;

@Service
@Transactional
public class UserDonationService {

	@Autowired
	UserDonationRepository userDonationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	DonationRepository donationRepository;
	
	
	public void addUserDonation(int donationId, int userId, String name, int money, String text) {
		User user = userRepository.findById(userId).get();
		Donation donation = donationRepository.findById(donationId).get();
		
		UserDonation userDonation = new UserDonation();
		userDonation.setUser(user);
		userDonation.setDonation(donation);
		userDonation.setName(name);
		userDonation.setMoney(money);
		userDonation.setText(text);		
		userDonation.setStatus(0);
		
		userDonationRepository.save(userDonation);
	}
	
	// Xác nhận một user đã donate hay chưa
	// Trả về id của đợt donation đó
	public int confirmUserDonation(int userDonationId) {
			UserDonation userDonation = userDonationRepository.findById(userDonationId).get();
			Donation donation = userDonation.getDonation();		// Get đợt donation tương ứng
			
			int newStatus = userDonation.getStatus() == 0 ? 1 : 0;
			int newMoney = newStatus == 0 ? donation.getMoney() - userDonation.getMoney() //Nếu hủy xác nhận thì trừ tiền
										: donation.getMoney() + userDonation.getMoney();  //Nếu xác nhận thì cộng thêm tiền
			
			userDonation.setStatus(newStatus);	// Set lại status mới
			donation.setMoney(newMoney);		// Set lại số tiền mới
			userDonationRepository.save(userDonation);	// Lưu lại cập nhật
			
			return userDonation.getDonation().getId();	// Trả về id của donation để redirect đúng trang detail donation đó
	}
}
