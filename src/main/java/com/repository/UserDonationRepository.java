package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.UserDonation;

public interface UserDonationRepository extends JpaRepository<UserDonation, Integer> {

}
