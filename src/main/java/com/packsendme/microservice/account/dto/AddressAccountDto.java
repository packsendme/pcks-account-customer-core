package com.packsendme.microservice.account.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "AddressAccountDto")
public class AddressAccountDto implements Serializable {
	
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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("address")
	private List<AddressDto> address;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<AddressDto> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDto> address) {
		this.address = address;
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
}
