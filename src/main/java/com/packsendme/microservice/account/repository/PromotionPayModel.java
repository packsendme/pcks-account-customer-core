 package com.packsendme.microservice.account.repository;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "VoucherPay")
public class PromotionPayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
    private String id;
    private String promotionName;
    private String promotionNumber;
	private String promotionEntity;
	private String promotionExpiry;
	private String promotionStatus;
	private String promotionValue;
	private Date dateCreation;
	private Date dateUpdate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getPromotionNumber() {
		return promotionNumber;
	}
	public void setPromotionNumber(String promotionNumber) {
		this.promotionNumber = promotionNumber;
	}
	public String getPromotionEntity() {
		return promotionEntity;
	}
	public void setPromotionEntity(String promotionEntity) {
		this.promotionEntity = promotionEntity;
	}
	public String getPromotionExpiry() {
		return promotionExpiry;
	}
	public void setPromotionExpiry(String promotionExpiry) {
		this.promotionExpiry = promotionExpiry;
	}
	public String getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public String getPromotionValue() {
		return promotionValue;
	}
	public void setPromotionValue(String promotionValue) {
		this.promotionValue = promotionValue;
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

	



	
}
