package com.packsendme.microservice.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.account.dto.AccountDto;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.service.AccountService;

@RestController
@RequestMapping("/account/customer")
public class CustomerController {

	
	@Autowired
	private AccountService accountService; 
	
	// ACCOUNT ENTITY

	
	//** BEGIN OPERATION: ACCOUNT FIRST ACCESS *************************************************//

	@PostMapping("/")
	public ResponseEntity<?> createAccount(
			@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.registerAccount(account);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateAccount(
			@Validated @RequestBody AccountDto account) throws Exception {
		return accountService.updateAccountPersonalData(account);
	}

	
	@PutMapping("/{username}/{usernamenew}/{dtAction}")
	public ResponseEntity<?> updateUsernameAccount(
			@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("usernamenew") String usernamenew,
			@Validated @PathVariable ("dtAction") String dtAction) throws Exception {
		return accountService.updateUsername(username,usernamenew,dtAction);
	}
	
	// ADDRESS ENTITY
	@PutMapping("/address/{username}")
	public ResponseEntity<?> updateAddressAccount(
			@Validated @PathVariable ("username") String username,
			@Validated @RequestBody AddressAccountDto addressAccount) throws Exception {
		return accountService.updateAddressAccountByUsername(username,addressAccount);
	}

	//** BEGIN OPERATION CRUD *************************************************//

	@GetMapping("/{username}/load")
	public ResponseEntity<?> loadAccount(
			@Validated @PathVariable ("username") String username) throws Exception {
		return accountService.findAccountToLoad(username);
	}

	@GetMapping("/{field}/{type}")
	public ResponseEntity<?> loadAccountByField(
			@Validated @PathVariable ("field") String field,
			@Validated @PathVariable ("type") String type) {
		return accountService.findAccountByField(field, type);
	}
	
	@GetMapping("/personalname/{username}")
	public ResponseEntity<?> loadFirstNameAccount(
			@Validated @PathVariable ("username") String username) {
		return accountService.findNamesAccountByUsername(username);
	}
	

}
