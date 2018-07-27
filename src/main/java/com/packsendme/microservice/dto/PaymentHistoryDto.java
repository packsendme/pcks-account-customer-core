package com.packsendme.microservice.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class PaymentHistoryDto {
	
	private @Id Long id;

	private int bin;
	
	private String cardType;
	
	private String descriptionTransaction;

	private int numberTransaction;

	private long cost;

	private LocalDateTime datetimeTransaction;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBin() {
		return bin;
	}

	public void setBin(int bin) {
		this.bin = bin;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getNumberTransaction() {
		return numberTransaction;
	}

	public void setNumberTransaction(int numberTransaction) {
		this.numberTransaction = numberTransaction;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public LocalDateTime getDatetimeTransaction() {
		return datetimeTransaction;
	}

	public void setDatetimeTransaction(LocalDateTime datetimeTransaction) {
		this.datetimeTransaction = datetimeTransaction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescriptionTransaction() {
		return descriptionTransaction;
	}

	public void setDescriptionTransaction(String descriptionTransaction) {
		this.descriptionTransaction = descriptionTransaction;
	}

	public PaymentHistoryDto(Long id, int bin, String cardType, String descriptionTransaction, int numberTransaction,
			long cost, LocalDateTime datetimeTransaction, String status) {
		super();
		this.id = id;
		this.bin = bin;
		this.cardType = cardType;
		this.descriptionTransaction = descriptionTransaction;
		this.numberTransaction = numberTransaction;
		this.cost = cost;
		this.datetimeTransaction = datetimeTransaction;
		this.status = status;
	}
	
	
	
}
