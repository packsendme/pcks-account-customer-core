package com.packsendme.microservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.lib.common.constants.Message;
import com.packsendme.lib.common.dto.Response;
import com.packsendme.microservice.account.model.Account;

@Component
public class TransformObject {
	
	@Autowired
	private Message msg;
	
	public Response<Account> transfAccountEntityToResponse(Account account) {
		Response<Account> response = new Response<Account>(msg.ERROR_COD, msg.ERROR_STATUS, account);
		return response;
	}

}
