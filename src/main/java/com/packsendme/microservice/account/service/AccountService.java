package com.packsendme.microservice.account.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.InfoMicroservices;
import com.packsendme.lib.common.dto.Response;
import com.packsendme.lib.common.logging.LogginExecution;
import com.packsendme.lib.common.logging.LogginImpl;
import com.packsendme.microservice.account.model.Account;
import com.packsendme.microservice.account.model.AccountRepository;
import com.packsendme.microservice.config.TransformObject;
import com.packsendme.microservice.dto.AccountDto;

@Service
public class AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private LogginImpl log;

	@Autowired
	private AccountRepository accountRep;
	
	@Autowired
	private Account account;
	
	@Autowired
	TransformObject transfObj;
	
	
	public Response<Account> createAccountService(AccountDto accountDTO) {
		ModelMapper modelMapper = new ModelMapper();
		log.info(InfoMicroservices.MC_ACCOUNT_OP_CREATE);
		try {
			account = modelMapper.map(accountDTO, Account.class);
			account = accountRep.save(account);
			Response<Account> accountSuccess = transfObj.transfAccountEntityToResponse(account);
			return accountSuccess;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			Response<Account> accountError = transfObj.transfAccountEntityToResponse(account);
			return accountError;
		}
	}

	public Response<Account> loadAccountService(String email) {
		log.info(InfoMicroservices.MC_ACCOUNT_OP_LOAD);
		try {
			account = accountRep.findAllByUserName(email);
			Response<Account> accountSuccess = transfObj.transfAccountEntityToResponse(account);
			return accountSuccess;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			Response<Account> accountError = transfObj.transfAccountEntityToResponse(account);
			return accountError;
		}
	}


	public Response<Account> cancelAccountService(String email) {
		log.info(InfoMicroservices.MC_ACCOUNT_OP_CANCEL);
		try {
			account = accountRep.cancelUserByStatus(email);
			Response<Account> accountSuccess = transfObj.transfAccountEntityToResponse(account);
			return accountSuccess;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			Response<Account> accountError = transfObj.transfAccountEntityToResponse(account);
			return accountError;
		}
	}

	public Response<Account> updateAccountService(AccountDto accountDTO) {
		log.info(InfoMicroservices.MC_ACCOUNT_OP_UPDATE);
		try {
			account = accountRep.updateAccount(account);
			Response<Account> accountSuccess = transfObj.transfAccountEntityToResponse(account);
			return accountSuccess;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			Response<Account> accountError = transfObj.transfAccountEntityToResponse(account);
			return accountError;
		}
	}
}
