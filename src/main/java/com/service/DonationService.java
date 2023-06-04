package com.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DonationDTO;
import com.entity.Donation;
import com.repository.DonationRepository;

@Service
@Transactional
public class DonationService {

	@Autowired
	private DonationRepository donationRepository;
	
	// Get tất cả các đợt donation
	public List<DonationDTO> getAllDonations() {
		return listDonationToListDonationDTO(donationRepository.findAll());
	}
	
	// Tạo mới 1 đợt donation
	public void addNewDonation(DonationDTO donationDTO) {
		Donation newDonation = new Donation(donationDTO);
		donationRepository.save(newDonation);
	}
	
	// Xóa 1 đợt donation bằng donationId
	public void deleteDonation(int donationId) {
		Optional<Donation> result = donationRepository.findById(donationId);
		if (result.isPresent()) {
			Donation donation = result.get();
			if (donation.getStatus() == 0) {	// Nếu donation có trạng thái là mới tạo thì mới được xóa
				donationRepository.delete(donation);
				System.out.println("Delete donation (id = " + donationId + ") successfully");
			}
		}
	}
	
	// Get 1 đợt donation bằng donationId
	public DonationDTO getDonation(int donationId) {
		Optional<Donation> result = donationRepository.findById(donationId);
		
		if (result.isPresent()) {
			Donation donation = result.get();
			DonationDTO donationDTO = new DonationDTO(donation);
			return donationDTO;
		}
		else return null;
	}
	
	public void changeStatus(int donationId) {
		Optional<Donation> result= donationRepository.findById(donationId);
		if (result.isPresent()) {
			Donation donation = result.get();
	// Kiểm tra status của donation để set donation cho phù hợp (mới tạo -> đang quyên góp -> kết thúc -> đóng quyên góp
			switch (donation.getStatus()) {
				case 0: donation.setStatus(1);
						break;
				case 1: donation.setStatus(2);
						break;
				case 2: donation.setStatus(3);
						break;
			}
			donationRepository.save(donation);
			System.out.println("Change donation status successfully");
		}
	}
	
	// Cập nhật 1 đợt donation theo các trường
	public void updateDonation(int donationId, String code, String name, Date startDate, Date endDate, 
							String organizationName, String phoneNumber, String description) {
		Optional<Donation> result = donationRepository.findById(donationId);
		if (result.isPresent()) {
			Donation donation = result.get();
			donation.setCode(code);
			donation.setName(name);
			donation.setStartDate(startDate);
			donation.setEndDate(endDate);
			donation.setOrganizationName(organizationName);
			donation.setPhoneNumber(phoneNumber);
			donation.setDescription(description);
			
			donationRepository.save(donation);
			System.out.println("Update donation successfully");
		}
	}
	
	// Phân trang cho donations
	public Page<DonationDTO> getPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Donation> page = donationRepository.findAll(pageable);
        
        Page<DonationDTO> pageDTO = page.map(donation -> new DonationDTO(donation));//Chuyển về Page<DonationDTO> truyền qua controller
        return pageDTO;
    } 
	
	
	
	// Hàm chuyển List<Donation> thành List<DonationDTO>
	public List<DonationDTO> listDonationToListDonationDTO(List<Donation> donations) {
		List<DonationDTO> donationsDTO = donations.stream()
			.map(donation -> new DonationDTO(donation))
			.collect(Collectors.toList());
		return donationsDTO;
	}
}
