package com.packsendme.microservice.account.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	private ArrayList<AddressModel> addressL;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("address")
	private List<AddressModel> address;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("payment")
	private List<PaymentModel> payment;
	
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
	public List<PaymentModel> getPayment() {
		return payment;
	}
	public void setPayment(List<PaymentModel> payment) {
		this.payment = payment;
	}
	
	public List<AddressModel> getAddress() {
		return address;
	}
	public void setAddress(List<AddressModel> address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public ArrayList<AddressModel> getAddressL() {
		return addressL;
	}
	public void setAddressL(ArrayList<AddressModel> addressL) {
		this.addressL = addressL;
	}
	
}
