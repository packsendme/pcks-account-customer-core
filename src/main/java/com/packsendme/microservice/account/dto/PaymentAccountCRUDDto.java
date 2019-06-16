package com.packsendme.microservice.account.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PaymentAccount")
public class PaymentAccountCRUDDto implements Serializable {
	
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
	
    private String cardName;
    private String cardType;
	private String cardNumber;
	private String cardExpiry;
	private String cardCVV;
	private String cardStatus;

	
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

	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardExpiry() {
		return cardExpiry;
	}
	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}
	public String getCardCVV() {
		return cardCVV;
	}
	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
}
