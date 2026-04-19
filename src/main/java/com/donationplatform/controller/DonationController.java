package com.donationplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donationplatform.dto.DonationRequest;
import com.donationplatform.dto.PaymentVerificationRequest;
import com.donationplatform.entity.Donation;
import com.donationplatform.service.DonationService;



@RestController
@CrossOrigin
@RequestMapping("/donation")
public class DonationController {
	
	private final DonationService service;
	
	public DonationController(DonationService service) {
		
		this.service =service;
	}
	
	@PostMapping("/create")
	public Donation createDonation(@RequestBody DonationRequest request) {
		
		    Donation donation =  service.createDonation(request);
		    
		    return donation;
	}
	
	@PostMapping("/verify")
	public String verify(@RequestBody PaymentVerificationRequest request) {
	    return service.verifyPayment(request);
	}
	
//	WEBHOOK IMPLEMENTATION
	
	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebhook(
	        @RequestBody String payload,
	        @RequestHeader(value = "X-Razorpay-Signature", required = false) String signature
	) {

	    System.out.println("Webhook received 🔥");
	    System.out.println(payload);
	    System.out.println("Signature: " + signature);

	    service.processWebhook(payload, signature);

	    return ResponseEntity.ok("Webhook processed");
	}

}
