package com.donationplatform.dto;

import lombok.Data;


public class PaymentVerificationRequest {
	
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    
	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}
	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}
	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}
	public String getRazorpaySignature() {
		return razorpaySignature;
	}
	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}
	@Override
	public String toString() {
		return "PaymentVerificationRequest [razorpayPaymentId=" + razorpayPaymentId + ", razorpayOrderId="
				+ razorpayOrderId + ", razorpaySignature=" + razorpaySignature + "]";
	}
	public PaymentVerificationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
