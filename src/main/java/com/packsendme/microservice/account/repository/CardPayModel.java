 package com.packsendme.microservice.account.repository;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class CardPayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
    private String id;
    private String cardEntity;
	private String cardNumber;
	private String cardExpiry;
	private String cardCVV;
	private String cardCountry;
	private String cardStatus;
	private Date dateCreation;
	private Date dateUpdate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCardEntity() {
		return cardEntity;
	}
	public void setCardEntity(String cardEntity) {
		this.cardEntity= cardEntity;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public String getCardCountry() {
		return cardCountry;
	}
	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}



	
}
