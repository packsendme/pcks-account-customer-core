package com.packsendme.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientException;
import com.packsendme.lib.common.constants.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.account.dao.AccountDAO;
import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.dto.PaymentsAccountDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.utility.PaymentAccountParser;


@Service
@ComponentScan("com.packsendme.lib.utility")
public class PaymentAccountService {

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private PaymentAccountParser paymentParser;
	
	public ResponseEntity<?> loadPaymentAccountAll(String username) throws Exception {
		AccountModel entity = new AccountModel();
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			
			if(entity.getPayment() != null){
				PaymentsAccountDto paymentAccountDto = paymentParser.parsePaymentAccountOpLoad(entity);
				Response<PaymentsAccountDto> responseObj = new Response<PaymentsAccountDto>(HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), paymentAccountDto);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseErrorObj = new Response<AccountModel>(HttpExceptionPackSend.FOUND_ACCOUNT.getAction(), null);
			return new ResponseEntity<>(responseErrorObj, HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> updatePaymentAccountByUsername(PaymentDto paymentDto) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.UPDATE_USERNAME.getAction(), entity);
		try {
			entity.setUsername(paymentDto.getUsername());
			entity = accountDAO.find(entity);

			if(entity != null) {
				AccountModel entityObj = paymentParser.parsePaymentAccountOpEdit(entity, paymentDto); 
				entity = accountDAO.update(entityObj);
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

	public ResponseEntity<?> deletePaymentAccountByUsername(PaymentDto paymentDto) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_DELETE.getAction(), entity);
		try {
			entity.setUsername(paymentDto.getUsername());
			entity = accountDAO.find(entity);

			if(entity != null) {
				AccountModel entityObj = paymentParser.parsePaymentAccountOpDelete(entity, paymentDto);
				entity = accountDAO.update(entityObj);
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
	
	public ResponseEntity<?> savePaymentAccountByUsername(PaymentDto paymentDto) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(HttpExceptionPackSend.ACCOUNT_DELETE.getAction(), entity);
		try {
			entity.setUsername(paymentDto.getUsername());
			entity = accountDAO.find(entity);

			if(entity != null) {
				AccountModel entityObj = paymentParser.parsePaymentOpSave(entity, paymentDto);
				entity = accountDAO.update(entityObj);
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


}
