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
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.account.dto.AccountDto;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.dto.PaymentAccountCRUDDto;
import com.packsendme.microservice.account.service.AccountService;

@RestController
public class AccountController {

	
	@Autowired
	private AccountService accountService; 
	
	
	//** BEGIN OPERATION: ACCOUNT FIRST ACCESS *************************************************//

	@PostMapping("/account")
	public ResponseEntity<?> createAccount(@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.registerAccount(account);
	}

	//** BEGIN OPERATION CRUD *************************************************//

	// ACCOUNT ENTITY
	@GetMapping("/account/{username}/load")
	public ResponseEntity<?> loadAccount(@Validated @PathVariable ("username") String username) throws Exception {
		return accountService.findAccountToLoad(username);
	}

	@GetMapping("/account/{email}")
	public ResponseEntity<?> validateEmailAccount(@Validated @PathVariable ("email") String email) {
		return accountService.findAccountByEmail(email);
	}
	
	@PutMapping("/account/{username}/{usernamenew}/{dtAction}")
	public ResponseEntity<?> changeUsernameForAccount(@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("usernamenew") String usernamenew,
			@Validated @PathVariable ("dtAction") String dtAction) throws Exception {
		return accountService.updateAccountByUsername(username,usernamenew,dtAction);
	}
	
	@PutMapping("/account")
	public ResponseEntity<?> changeAccount(@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.updateAccountPersonalData(account);
	}
	
	// PAYMENT ENTITY
	@GetMapping("/account/payment/{username}")
	public ResponseEntity<?> getPayment(@Validated @PathVariable ("username") String username) throws Exception {
		return accountService.findPaymentUserByUsername(username);
	}

	@PutMapping("/account/payment")
	public ResponseEntity<?> changePayment(@Validated @RequestBody PaymentAccountCRUDDto paymentDto) throws Exception {
		return accountService.updatePaymentAccountByUsername(paymentDto);
	}
	
	@DeleteMapping("/account/payment")
	public ResponseEntity<?> removePayment(@Validated @RequestBody PaymentAccountCRUDDto paymentDto) throws Exception {
		return accountService.deletePaymentAccountByUsername(paymentDto);
	}
	
	@PostMapping("/account/payment")
	public ResponseEntity<?> savePayment(@Validated @RequestBody PaymentAccountCRUDDto paymentDto) throws Exception {
		return accountService.savePaymentAccountByUsername(paymentDto);
	}

	// ADDRESS ENTITY
	@PutMapping("/account/address")
	public ResponseEntity<?> changeAddressAccount(@Validated @RequestBody AddressAccountDto addressAccount) throws Exception {
		return accountService.updateAddressAccountByUsername(addressAccount);
	}
	


}
