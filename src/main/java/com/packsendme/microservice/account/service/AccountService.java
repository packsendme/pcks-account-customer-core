package com.packsendme.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientException;
import com.packsendme.lib.common.constants.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.account.controller.IAMClient;
import com.packsendme.microservice.account.dao.AccountDAO;
import com.packsendme.microservice.account.repository.AccountModel;

@Service
@ComponentScan("com.packsendme.microservice.dao")
public class AccountService {
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private IAMClient iamClient; 

	public ResponseEntity<?> registerAccountAndUserEnable(AccountModel account) {
		AccountModel accountSave = null;
		try {
			accountSave = accountDAO.add(account); 
			if(accountSave != null) {
				ResponseEntity<?> userAccessEnable = iamClient.enableUserAccess(account.getUsername(),account.getPassword());
				if(userAccessEnable.getStatusCode() == HttpStatus.ACCEPTED) {
					Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATED.value(), HttpExceptionPackSend.ACCOUNT_CREATED.getAction(), accountSave);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					cancelAccount(account.getUsername());
					Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.value(), HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.value(), HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			cancelAccount(account.getUsername());
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION.value(), HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> cancelAccount(String username) {
		try {
			AccountModel entity = new AccountModel();

			ResponseEntity<?> responseIAMDelete = iamClient.cancelUserAccessRegistration(username);
			if ( responseIAMDelete.getStatusCode() == HttpStatus.ACCEPTED) {
				entity.setUsername(username);
				entity = accountDAO.find(entity);
				accountDAO.remove(entity);
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_DELETE.value(), HttpExceptionPackSend.ACCOUNT_DELETE.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION.value(), HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	public ResponseEntity<?> getAccount(String username) {
		AccountModel entity = new AccountModel();
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);

			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_ACCOUNT.value(), HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.NOT_FOUND_ACCOUNT.value(), HttpExceptionPackSend.NOT_FOUND_ACCOUNT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
	}


	public ResponseEntity<?> updateAllAccount(AccountModel account) {
		AccountModel entity = new AccountModel();
		try {
			
			System.out.println(" updateAllAccount");

			entity.setUsername(account.getUsername());
			entity = accountDAO.find(entity);
			
			System.out.println(" ENTROU NULL "+ entity.getName() );

			entity.setEmail(account.getEmail());
			entity.setName(account.getName());
			entity.setLastName(account.getLastName());
			entity.setAddress(account.getAddress());
			entity = accountDAO.update(entity);
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.UPDATE_ACCOUNT.value(), HttpExceptionPackSend.UPDATE_ACCOUNT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION.value(), HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateUsernameAccount(String username, String usernamenew) {
		AccountModel entity = new AccountModel();
		System.out.println(" updateUsernameAccount username "+ username);
		System.out.println(" updateUsernameAccount usernamenew "+ usernamenew);

		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			entity.setUsername(usernamenew);
			entity = accountDAO.update(entity);
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.USERNAME_UPDATE.value(), HttpExceptionPackSend.USERNAME_UPDATE.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION.value(), HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> getEmail(String email) {
		AccountModel entity = new AccountModel();
		try {
			entity.setEmail(email);
			entity = accountDAO.find(entity);
			if(entity != null) {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_EMAIL.value(), HttpExceptionPackSend.FOUND_EMAIL.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.NOT_FOUND_EMAIL.value(), HttpExceptionPackSend.NOT_FOUND_EMAIL.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION.value(), HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
