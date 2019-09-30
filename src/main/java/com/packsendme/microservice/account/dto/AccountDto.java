package com.packsendme.microservice.account.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "AccountDto")
public class AccountDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String username;
	private String email;
	private String name;
	private String password;
	private String lastName;
	private String country;
	private String dateOperation;
	
	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("address")
	private List<AddressDto> address;
	
	
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
	public List<AddressDto> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDto> address) {
		this.address = address;
	}
	public String getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(String dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
