package com.packsendme.microservice.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class PaymentDto {
	
	private @Id String id;
	
	private String cardholderName;
	
	private String cardType;
	
	private int bin;
	
	private Date expirationDate;
	
	private int expirationMonth;
	
	private int expirationYear;
	
	private String billingAddress;
	
	private int securityNumber;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getBin() {
		return bin;
	}

	public void setBin(int bin) {
		this.bin = bin;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public int getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public int getSecurityNumber() {
		return securityNumber;
	}

	public void setSecurityNumber(int securityNumber) {
		this.securityNumber = securityNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PaymentDto(String id, String cardholderName, String cardType, int bin, Date expirationDate, int expirationMonth,
			int expirationYear, String billingAddress, int securityNumber, String status) {
		super();
		this.id = id;
		this.cardholderName = cardholderName;
		this.cardType = cardType;
		this.bin = bin;
		this.expirationDate = expirationDate;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.billingAddress = billingAddress;
		this.securityNumber = securityNumber;
		this.status = status;
	}
	
	
}
