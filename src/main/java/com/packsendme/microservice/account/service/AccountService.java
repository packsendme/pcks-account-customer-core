package com.packsendme.microservice.account.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientException;
import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.lib.utility.ConvertFormat;
import com.packsendme.microservice.account.controller.IAMClient;
import com.packsendme.microservice.account.dao.CustomerDAO;
import com.packsendme.microservice.account.dto.AccountDto;
import com.packsendme.microservice.account.dto.AccountLoadDto;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.dto.NamesAccountDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.utility.AccountParser;

@Service
@ComponentScan("com.packsendme.lib.utility")
public class AccountService {
	
	@Autowired
	private CustomerDAO accountDAO;
	
	@Autowired
	private IAMClient iamClient; 

	@Autowired
	private ConvertFormat convertObj;
 
	@Autowired
	private AccountParser accountParser;
	
	public ResponseEntity<?> registerAccount(AccountDto accountDto) throws Exception {
		AccountModel accountSave = null;
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.CREATED_ACCOUNT.getAction(), accountSave);
		Date dateOperation = convertObj.convertStringToDate(accountDto.getDateOperation());
		
		AccountModel account = new AccountModel(accountDto.getUsername(), accountDto.getEmail(), accountDto.getName(), accountDto.getLastName(),
				accountDto.getCountry(),dateOperation);
 
		try {
			accountSave = accountDAO.add(account); 
			if(accountSave != null) {
				// Call IAMService - To allows User Access 
				ResponseEntity<?> userAccessEnable = iamClient.createUser(accountDto.getUsername(),
						accountDto.getPassword(), accountDto.getDateOperation());
				if(userAccessEnable.getStatusCode() == HttpStatus.ACCEPTED) {
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				// Call IAMService - Error update that delete account
				else {
					accountDAO.remove(accountSave);
					return new ResponseEntity<>(responseObj, HttpStatus.BAD_GATEWAY);
				}
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			accountDAO.remove(accountSave);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateUsername(String username, String usernamenew, String dtAction) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.UPDATE_USERNAME.getAction(), entity);
		Date dtUpdate = convertObj.convertStringToDate(dtAction);
		try {
			entity.setUsername(username);
			AccountModel account = accountDAO.find(entity);
			if(account != null) {
				account.setDateUpdate(dtUpdate);
				account.setUsername(usernamenew);
				account = accountDAO.update(account);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
	public ResponseEntity<?> findAccountToLoad(String username) {
		AccountModel entity = new AccountModel();
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			
			if(entity != null){
				AccountLoadDto accountLoadDto = accountParser.parseAccountModelToAccountLoad(entity);
				Response<AccountLoadDto> responseObj = new Response<AccountLoadDto>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), accountLoadDto);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseErrorObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), entity);
			return new ResponseEntity<>(responseErrorObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> findNamesAccountByUsername(String username) {
		AccountModel entity = new AccountModel();
		NamesAccountDto nameAccountdDto = new NamesAccountDto();
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			
			if(entity != null){
				nameAccountdDto.setFirstName(entity.getName());
				nameAccountdDto.setLastName(entity.getLastName());
				//Response<NamesAccountDto> responseObj = new Response<NamesAccountDto>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), nameAccountdDto);
				return new ResponseEntity<>(nameAccountdDto, HttpStatus.OK);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseErrorObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), entity);
			return new ResponseEntity<>(responseErrorObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	public ResponseEntity<?> updateAccountPersonalData(AccountDto accountDto) throws Exception {
		AccountModel accountFind = new AccountModel();
		accountFind.setUsername(accountDto.getUsername());
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.UPDATE_ACCOUNT.getAction(), null);

		try {
			AccountModel entity = accountDAO.find(accountFind);
			if(entity != null) {
				entity.setUsername(accountDto.getUsername());
				entity.setEmail(accountDto.getEmail());
				entity.setName(accountDto.getName());
				entity.setLastName(accountDto.getLastName());
				entity.setCountry(accountDto.getCountry());
				entity.setDateUpdate(convertObj.convertStringToDate(accountDto.getDateOperation()));
				entity = accountDAO.update(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateAddressAccountByUsername(String username, AddressAccountDto addressAccount) throws Exception {
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.UPDATE_ACCOUNT.getAction(), null);
		try {
			AccountModel accountObj = new AccountModel();
			accountObj.setUsername(username);
			AccountModel accountFind = accountDAO.find(accountObj);
			
			// Parser Account Entity - Account Address
			AccountModel entity = accountParser.parseAddressDtoToAccountModel(accountFind, addressAccount);
			
			if(entity != null) {
				entity = accountDAO.update(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public ResponseEntity<?> findAccountByField(String field, String type) {
		AccountModel accountEntity = new AccountModel();
		Response<AccountModel> responseObj = null;
		AccountModel entity = null;
		try {
			if (type.equals("email")){
				accountEntity.setEmail(field);
				entity = accountDAO.find(accountEntity);
			}
			else if (type.equals("username")){
				accountEntity.setUsername(field);
				entity = accountDAO.find(accountEntity);
			} 
				
			if(entity != null) {
				responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			else {
				responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.NOT_FOUND_ACCOUNT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
