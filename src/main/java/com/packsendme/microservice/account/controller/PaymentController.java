package com.packsendme.microservice.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.service.PaymentAccountService;

@RestController
@RequestMapping("/account/payment")
public class PaymentController {

	@Autowired
	private PaymentAccountService paymentAccountService;
	
	// PAYMENT ENTITY
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getPayment(
			@Validated @PathVariable ("username") String username) throws Exception {
		return paymentAccountService.loadPaymentAccountAll(username);
	}
	
	@GetMapping("/{username}/{codnum}")
	public ResponseEntity<?> getPaymentByCodnum(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("codnum") String codnum) throws Exception {
		return paymentAccountService.loadPaymentAccountByCod(username, codnum);
	}

	@PutMapping("/{username}/{codnumOld}")
	public ResponseEntity<?> changePayment(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("codnumOld") String codnumOld, 
			@Validated @RequestBody PaymentDto paymentDto) throws Exception {
		return paymentAccountService.updatePaymentAccountByUsername(username,codnumOld,paymentDto);
	}
	
	@DeleteMapping("/{username}/{payCodenum}/{payType}")
	public ResponseEntity<?> removePayment(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("payCodenum") String payCodenum,
			@Validated @PathVariable ("payType") String payType) throws Exception {
		return paymentAccountService.deletePaymentAccountByUsername(username, payCodenum, payType);
	}
	
	@PostMapping("/{username}")
	public ResponseEntity<?> addPayment(
			@Validated @PathVariable ("username") String username,
			@Validated @RequestBody PaymentDto paymentDto) throws Exception {
		return paymentAccountService.savePaymentAccountByUsername(username,paymentDto);
	}

}
