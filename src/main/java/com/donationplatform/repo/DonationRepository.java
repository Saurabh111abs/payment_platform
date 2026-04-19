package com.donationplatform.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donationplatform.entity.Donation;



@Repository
public interface DonationRepository  extends JpaRepository<Donation, Long>{

	Optional<Donation> findByOrderId(String orderId);
	
	

}
