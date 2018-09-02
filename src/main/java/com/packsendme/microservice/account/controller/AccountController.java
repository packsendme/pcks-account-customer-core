package com.packsendme.microservice.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.service.AccountService;

@RestController
@RequestMapping("/account/api/")
public class AccountController {

	
	@Autowired
	private AccountService accountService; 
	

	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, path="/create", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createAccount(@Validated @RequestBody AccountModel account) {
		return accountService.registerAccountAndUserEnable(account);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, path="/cancel/{username}", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> cancelAccountActiva(@Validated @PathVariable String username) {
		return accountService.cancelAccount(username);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, path="/update/username/{username}/{usernamenew}", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> changeUsernameAccount(@Validated @PathVariable ("username") String username,
			@Validated @PathVariable ("usernamenew") String usernamenew) {
		return accountService.updateUsernameAccount(username,usernamenew);
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, path="/update", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> changeAccountData(@Validated @RequestBody AccountModel account) {
		return accountService.updateAllAccount(account);
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, path="/validate/email/{email}", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> emailAccountValidate(@Validated @PathVariable ("email") String email) {
		return accountService.getEmail(email);
	}

}
