package com.packsendme.microservice.account.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "PaymentAccount")
public class PaymentsAccountDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private String username;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("payment")
	private List<PaymentDto> payment;

	
	public List<PaymentDto> getPayment() {
		return payment;
	}
	public void setPayment(List<PaymentDto> payment) {
		this.payment = payment;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
