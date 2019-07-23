package com.packsendme.microservice.account.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class AccountModel implements Serializable {
	
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
	private String email;
	private String name;
	private String lastName;
	private Date dateCreation;
	private Date dateUpdate;
	private List<AddressModel> address;
	private List<PaymentModel> payment;

	
    public AccountModel() {
	}

	public AccountModel(String username, String email, String name, String lastName,Date dateCreation,Date dateUpdate) {
		super();
		this.username = username;
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.dateCreation = dateCreation;
		this.dateUpdate = dateUpdate;
	}
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	public List<AddressModel> getAddress() {
		return address;
	}

	public void setAddress(List<AddressModel> address) {
		this.address = address;
	}

	public List<PaymentModel> getPayment() {
		return payment;
	}

	public void setPayment(List<PaymentModel> payment) {
		this.payment = payment;
	}
 
	
}
