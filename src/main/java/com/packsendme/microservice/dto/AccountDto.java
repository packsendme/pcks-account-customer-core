package com.packsendme.microservice.dto;

import org.springframework.data.annotation.Id;

public class AccountDto {
	
	private @Id String id;
	private String name;
	private String lastName;
	private String addressHome;
	private String addressJob;
	private String phone;
	
	private AccessUserDto accessUser;
	private SubmissionsHistoryDto submission;
	private PaymentDto payment;
	private PaymentHistoryDto paymentHistory;
	
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public SubmissionsHistoryDto getSubmission() {
		return submission;
	}
	public void setSubmission(SubmissionsHistoryDto submission) {
		this.submission = submission;
	}
	public PaymentDto getPayment() {
		return payment;
	}
	public void setPayment(PaymentDto payment) {
		this.payment = payment;
	}
	public PaymentHistoryDto getPaymentHistory() {
		return paymentHistory;
	}
	public void setPaymentHistory(PaymentHistoryDto paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
	public AccessUserDto getAccessUser() {
		return accessUser;
	}
	public void setAccessUser(AccessUserDto accessUser) {
		this.accessUser = accessUser;
	}
	
	public AccountDto(String id, String name, String lastName, String addressHome, String addressJob, String email,
			String phone, SubmissionsHistoryDto submission, PaymentDto payment, PaymentHistoryDto paymentHistory,
			AccessUserDto accessUser) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.addressHome = addressHome;
		this.addressJob = addressJob;
		this.phone = phone;
		this.submission = submission;
		this.payment = payment;
		this.paymentHistory = paymentHistory;
		this.accessUser = accessUser;
	}

	
	

}
