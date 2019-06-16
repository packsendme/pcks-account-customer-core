package com.packsendme.microservice.account.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packsendme.microservice.account.repository.PaymentModel;

@Document(collection = "PaymentAccount")
public class PaymentAccountCollectionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
    private String id;
	private String username;
	private String dateUpdate;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("payment")
	private List<PaymentDto> payment;

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public List<PaymentDto> getPayment() {
		return payment;
	}
	public void setPayment(List<PaymentDto> payment) {
		this.payment = payment;
	}
}
