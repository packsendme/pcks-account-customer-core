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

import com.packsendme.microservice.account.dto.AccountDto;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.service.AccountService;
import com.packsendme.microservice.account.service.PaymentAccountService;

@RestController
@RequestMapping("/account")
public class CustomerController {

	
	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private PaymentAccountService paymentAccountService;
	
	
	//** BEGIN OPERATION: ACCOUNT FIRST ACCESS *************************************************//

	@PostMapping("/customer")
	public ResponseEntity<?> createAccount(
			@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.registerAccount(account);
	}

	//** BEGIN OPERATION CRUD *************************************************//

	// ACCOUNT ENTITY
	@GetMapping("/customer/{username}/load")
	public ResponseEntity<?> loadAccount(
			@Validated @PathVariable ("username") String username) throws Exception {
		return accountService.findAccountToLoad(username);
	}

	@GetMapping("/customer/{field}/{type}")
	public ResponseEntity<?> loadAccountByField(
			@Validated @PathVariable ("field") String field,
			@Validated @PathVariable ("type") String type) {
		return accountService.findAccountByField(field, type);
	}
	
	@GetMapping("/customer/personalname/{username}")
	public ResponseEntity<?> loadFirstNameAccount(
			@Validated @PathVariable ("username") String username) {
		return accountService.findNamesAccountByUsername(username);
	}
	
	@PutMapping("/customer/{username}/{usernamenew}/{dtAction}")
	public ResponseEntity<?> changeUsernameForAccount(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("usernamenew") String usernamenew,
			@Validated @PathVariable ("dtAction") String dtAction) throws Exception {
		return accountService.updateAccountByUsername(username,usernamenew,dtAction);
	}
	
	@PutMapping("/customer")
	public ResponseEntity<?> changeAccount(
			@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.updateAccountPersonalData(account);
	}
	
	// PAYMENT ENTITY
	
	@GetMapping("/payment/{username}")
	public ResponseEntity<?> getPayment(
			@Validated @PathVariable ("username") String username) throws Exception {
		return paymentAccountService.loadPaymentAccountAll(username);
	}
	
	@GetMapping("/payment/{username}/{codnum}")
	public ResponseEntity<?> getPaymentByCodnum(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("codnum") String codnum) throws Exception {
		return paymentAccountService.loadPaymentAccountByCod(username, codnum);
	}

	@PutMapping("/payment/{username}/{codnumOld}")
	public ResponseEntity<?> changePayment(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("codnumOld") String codnumOld, 
			@Validated @RequestBody PaymentDto paymentDto) throws Exception {
		return paymentAccountService.updatePaymentAccountByUsername(username,codnumOld,paymentDto);
	}
	
	@DeleteMapping("/payment/{username}/{payCodenum}/{payType}")
	public ResponseEntity<?> removePayment(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("payCodenum") String payCodenum,
			@Validated @PathVariable ("payType") String payType) throws Exception {
		return paymentAccountService.deletePaymentAccountByUsername(username, payCodenum, payType);
	}
	
	@PostMapping("/payment/{username}")
	public ResponseEntity<?> addPayment(
			@Validated @PathVariable ("username") String username,
			@Validated @RequestBody PaymentDto paymentDto) throws Exception {
		return paymentAccountService.savePaymentAccountByUsername(username,paymentDto);
	}

	// ADDRESS ENTITY
	@PutMapping("/address/{username}")
	public ResponseEntity<?> changeAddressAccount(
			@Validated @PathVariable ("username") String username,
			@Validated @RequestBody AddressAccountDto addressAccount) throws Exception {
		return accountService.updateAddressAccountByUsername(username,addressAccount);
	}
	


}
