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
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.dao.AccountDAO;

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
				ResponseEntity<?> userAccessEnable = iamClient.enableUserAccess(account.getUserName(),account.getPassword());
				if(userAccessEnable.getStatusCode() == HttpStatus.ACCEPTED) {
					Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATED, HttpExceptionPackSend.ACCOUNT_CREATED.toString(), accountSave);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					cancelAccount(account.getUserName());
					Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATE_FAIL, HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.toString(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_CREATE_FAIL, HttpExceptionPackSend.ACCOUNT_CREATE_FAIL.toString(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			cancelAccount(account.getUserName());
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION, HttpExceptionPackSend.FAIL_EXECUTION.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> cancelAccount(String username) {
		try {
			AccountModel entity = new AccountModel();

			ResponseEntity<?> responseIAMDelete = iamClient.cancelUserAccessRegistration(username);
			if ( responseIAMDelete.getStatusCode() == HttpStatus.ACCEPTED) {
				entity.setUserName(username);
				entity = accountDAO.find(entity);
				accountDAO.remove(entity);
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_DELETE, HttpExceptionPackSend.ACCOUNT_DELETE.toString(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION, HttpExceptionPackSend.FAIL_EXECUTION.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	public ResponseEntity<?> getAccount(String username) {
		AccountModel entity = new AccountModel();
		try {
			entity.setUserName(username);
			entity = accountDAO.find(entity);

			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_ACCOUNT, HttpExceptionPackSend.FOUND_ACCOUNT.toString(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.NOT_FOUND_ACCOUNT, HttpExceptionPackSend.NOT_FOUND_ACCOUNT.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
	}


	public ResponseEntity<?> updateAllAccount(AccountModel account) {
		AccountModel entity = new AccountModel();
		try {
			
			System.out.println(" updateAllAccount");

			entity.setUserName(account.getUserName());
			entity = accountDAO.find(entity);
			
			System.out.println(" ENTROU NULL "+ entity.getName() );

			entity.setEmail(account.getEmail());
			entity.setName(account.getName());
			entity.setLastName(account.getLastName());
			entity.setAddress(account.getAddress());
			entity.setPayment(account.getPayment());
			
			entity = accountDAO.update(entity);
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.UPDATE_ACCOUNT, HttpExceptionPackSend.UPDATE_ACCOUNT.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION, HttpExceptionPackSend.FAIL_EXECUTION.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateUsernameAccount(String username, String usernamenew) {
		AccountModel entity = new AccountModel();
		System.out.println(" updateUsernameAccount username "+ username);
		System.out.println(" updateUsernameAccount usernamenew "+ usernamenew);

		try {
			entity.setUserName(username);
			entity = accountDAO.find(entity);
			entity.setUserName(usernamenew);
			entity = accountDAO.update(entity);
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.USERNAME_UPDATE, HttpExceptionPackSend.USERNAME_UPDATE.toString(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION, HttpExceptionPackSend.FAIL_EXECUTION.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> getEmail(String email) {
		AccountModel entity = new AccountModel();
		try {
			entity.setEmail(email);
			entity = accountDAO.find(entity);
			if(entity != null) {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_EMAIL, HttpExceptionPackSend.FOUND_EMAIL.toString(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.NOT_FOUND_EMAIL, HttpExceptionPackSend.NOT_FOUND_EMAIL.toString(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FAIL_EXECUTION, HttpExceptionPackSend.FAIL_EXECUTION.toString(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
