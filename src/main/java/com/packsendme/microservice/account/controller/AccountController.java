package com.packsendme.microservice.account.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.lib.common.constants.InfoMicroservices;
import com.packsendme.lib.common.constants.Message;
import com.packsendme.lib.common.dto.Response;
import com.packsendme.microservice.account.model.Account;
import com.packsendme.microservice.account.service.AccountService;
import com.packsendme.microservice.dto.AccountDto;

@RestController
@RequestMapping("/account")
public class AccountController {

	
	@Autowired
	private AccountService accountService; 
	
	@Autowired
	private Response<Account> response;
	
	@Autowired
	private InfoMicroservices info;
	
	@Autowired
	private Message msg;

	@Autowired
	private Account account;

	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, path="/createaccountuser", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public Response<Account> createAccountUser(@Valid @RequestBody AccountDto accountDTO) {
		
		Date nowDate = Calendar.getInstance().getTime();
		
		try {
			accountService.createAccountService(accountDTO);
		 }
		 catch (Exception e) {

		 }
		return response;
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.GET, path="/loadaccount", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void loadAccount(@Valid @RequestBody AccountDto email) {
		 try {
			 
		 }
		 catch (Exception e) {
		}
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.DELETE, path="/cancelaccount", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void cancelAccount(@Valid @RequestBody AccountDto email) {
		 try {
			 
		 }
		 catch (Exception e) {
		}
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.PUT, path="/updateaccount", 
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateAccount(@Valid @RequestBody AccountDto accountDTO) {
		 try {
			 
		 }
		 catch (Exception e) {
		}
	}

}
