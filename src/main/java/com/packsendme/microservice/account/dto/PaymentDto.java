 package com.packsendme.microservice.account.dto;

import java.io.Serializable;
import java.util.Date;

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
    private String payPersonalName;
    private String payCodenum;
	private String payEntity;
	private String payType;
	private String payCountry;
	private String payExpiry;
	private String payStatus;
	private String payValue;
	private Date dateCreation;
	private Date dateUpdate;	
	
	public String getPayPersonalName() {
		return payPersonalName;
	}
	public void setPayPersonalName(String payPersonalName) {
		this.payPersonalName = payPersonalName;
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
