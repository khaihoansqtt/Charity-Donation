package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

}
