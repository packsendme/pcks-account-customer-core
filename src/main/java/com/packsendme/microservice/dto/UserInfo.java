package com.packsendme.microservice.dto;

import org.springframework.data.annotation.Id;


public class UserInfo {
	
	private @Id String id;
	
	private String name;
	
	private String lastName;
	
	private String addressHome;
	
	private String addressJob;
	
	private String email;
	
	private String phone;

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

	public String getAddressHome() {
		return addressHome;
	}

	public void setAddressHome(String addressHome) {
		this.addressHome = addressHome;
	}

	public String getAddressJob() {
		return addressJob;
	}

	public void setAddressJob(String addressJob) {
		this.addressJob = addressJob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserInfo(String id, String name, String lastName, String addressHome, String addressJob, String email,
			String phone) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.addressHome = addressHome;
		this.addressJob = addressJob;
		this.email = email;
		this.phone = phone;
	}


	
	
	

}
