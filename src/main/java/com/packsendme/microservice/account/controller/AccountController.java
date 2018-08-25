package com.packsendme.microservice.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.account.model.Account;
import com.packsendme.microservice.account.service.AccountService;

@RestController
@RequestMapping("/account/api")
public class AccountController {

	
	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private IAMClient iamClient; 

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, path="/create", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createAccount(@Validated @RequestBody Account account) {
		try {
			accountService.addAccount(account);
			iamClient.createUserAccess(account.getUserName(),account.getPassword());

			System.out.println(" account.getUserName()  "+ account.getUserName());
			System.out.println(" account.getPassword()  "+ account.getPassword());

			Response<Account> responseObj = new Response<Account>(HttpStatus.CREATED, HttpStatus.CREATED.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);

		}
		catch (Exception e) {
			System.out.println(" Exception "+ account.getUserName());
			accountService.deleteAccount(account.getUserName());
			iamClient.deleteUserAccess(account.getUserName());
			e.printStackTrace();
			Response<Account> responseObj = new Response<Account>(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
