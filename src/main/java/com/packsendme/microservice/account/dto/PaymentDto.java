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
    private String payName;
    private String payCodenum;
	private String payEntity;
	private String payType;
	private String payCountry;
	private String payExpiry;
	private String payStatus;
	private String payValue;
	private String dateOperation;
	
	
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
	public String getPayEntity() {
		return payEntity;
	}
	public void setPayEntity(String payEntity) {
		this.payEntity = payEntity;
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
	public String getPayCountry() {
		return payCountry;
	}
	public void setPayCountry(String payCountry) {
		this.payCountry = payCountry;
	}
	public String getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(String dateOperation) {
		this.dateOperation = dateOperation;
	}

	
	
    




	
}
