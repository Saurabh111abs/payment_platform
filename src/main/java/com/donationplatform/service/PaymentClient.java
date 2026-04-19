package com.donationplatform.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentClient {
	
	private final RazorpayClient client;
	
	 PaymentClient(RazorpayClient client) {
	  
		this.client = client;
		
	}
	
	
	public String createOrder(Double amount) {
		
	      try {
	            JSONObject options = new JSONObject();
	            options.put("amount", amount * 100); // paisa to rupees
	            options.put("currency", "INR");

	            Order order = client.orders.create(options);

	            return order.get("id");
	        } catch (Exception e) {
	            throw new RuntimeException("Payment order creation failed");
	        }
		
		
	}

}
