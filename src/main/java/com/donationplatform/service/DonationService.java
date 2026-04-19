package com.donationplatform.service;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.donationplatform.dto.DonationRequest;
import com.donationplatform.dto.PaymentVerificationRequest;
import com.donationplatform.entity.Donation;
import com.donationplatform.repo.DonationRepository;

@Service
public class DonationService {

	private final DonationRepository reporDonationRepository;
	private final PaymentClient client;
	private final PaymentService paymentService;

	public DonationService(DonationRepository reporDonationRepository, PaymentClient client,
			PaymentService paymentService)
	{
		this.reporDonationRepository = reporDonationRepository;
		this.client = client;
		this.paymentService = paymentService;
	}

	// DONATION SERVICE
	
	public Donation createDonation(DonationRequest request)

	{
		// 1. Create donation
		Donation donation =  new Donation();
		donation.setName(request.getName());
		donation.setAmount(request.getAmount());
		donation.setStatus("CREATED");
		donation.setCreatedAt(LocalDateTime.now());

		// 2. Create Razorpay Order
		String orderId = client.createOrder(request.getAmount());

		// 3. Save orderId
		donation.setOrderId(orderId);

		return reporDonationRepository.save(donation);
	}
	
	// verify payment service
	

	public String verifyPayment(PaymentVerificationRequest request) {

		boolean isValid = paymentService.verifySignature(
				request.getRazorpayOrderId(),
				request.getRazorpayPaymentId(),
				request.getRazorpaySignature()
				);

		if (isValid) {
			Donation donation = reporDonationRepository.findByOrderId(request.getRazorpayOrderId())
					.orElseThrow(() -> new RuntimeException("Donation not found"));

			donation.setStatus("SUCCESS");
			donation.setPaymentId(request.getRazorpayPaymentId());

			reporDonationRepository.save(donation);

			return "Payment verified successfully";
		} else {
			throw new RuntimeException("Invalid payment signature");
		}

	}
	
	// webhook service 

	public void processWebhook(String payload, String signature) {
	    try {
	        JSONObject json = new JSONObject(payload);

	        String event = json.getString("event");
	        System.out.println(event);

	        if ("payment.captured".equals(event)) {

	            JSONObject paymentEntity = json
	                    .getJSONObject("payload")
	                    .getJSONObject("payment")
	                    .getJSONObject("entity");

	            String orderId = paymentEntity.getString("order_id");
	            String paymentId = paymentEntity.getString("id");

	            Donation donation = reporDonationRepository.findByOrderId(orderId)
	                    .orElseThrow(() -> new RuntimeException("Donation not found"));

	            donation.setStatus("SUCCESS");
	            donation.setPaymentId(paymentId);

	            reporDonationRepository.save(donation);

	            System.out.println("Webhook payment updated");
	        }

	    } catch (Exception e) {
	        throw new RuntimeException("Webhook processing failed");
	    }}
	}

