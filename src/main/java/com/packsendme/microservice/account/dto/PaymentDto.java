 package com.packsendme.microservice.account.dto;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PaymentDto")
public class PaymentDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String username;
    private String payName;
    private String payCodenum;
	private String payGeneralType;
	private String payType;
	private String payExpiry;
	private String payStatus;
	private String payValue;
	private String dateCreation;
	private String dateUpdate;
	
	
	
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	 
	public String getPayExpiry() {
		return payExpiry;
	}
	public void setPayExpiry(String payExpiry) {
		this.payExpiry = payExpiry;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayValue() {
		return payValue;
	}
	public void setPayValue(String payValue) {
		this.payValue = payValue;
	}
	public String getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public String getPayGeneralType() {
		return payGeneralType;
	}
	public void setPayGeneralType(String payGeneralType) {
		this.payGeneralType = payGeneralType;
	}
	public String getPayCodenum() {
		return payCodenum;
	}
	public void setPayCodenum(String payCodenum) {
		this.payCodenum = payCodenum;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	
    




	
}
