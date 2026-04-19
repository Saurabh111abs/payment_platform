package com.donationplatform.dto;

import lombok.Data;

@Data
public class DonationRequest {
	
	private String name;
	private Double amount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "DonationRequest [name=" + name + ", amount=" + amount + "]";
	}
	public DonationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
