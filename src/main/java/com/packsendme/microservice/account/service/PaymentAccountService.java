package com.packsendme.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientException;
import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.account.dao.CustomerDAO;
import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.dto.PaymentsAccountDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.repository.CardPayModel;
import com.packsendme.microservice.account.repository.PaymentModel;
import com.packsendme.microservice.account.utility.PaymentAccountParser;


@Service
@ComponentScan("com.packsendme.lib.utility")
public class PaymentAccountService {

	@Autowired
	private CustomerDAO accountDAO;

	@Autowired
	private PaymentAccountParser paymentParser;
	
	public ResponseEntity<?> loadPaymentAccountAll(String username) throws Exception {
		AccountModel entity = new AccountModel();
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			
			if(entity.getPayment() != null){
				PaymentsAccountDto paymentAccountDto = paymentParser.parsePaymentAccountOpLoad(entity);
				Response<PaymentsAccountDto> responseObj = new Response<PaymentsAccountDto>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), paymentAccountDto);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseErrorObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), null);
			return new ResponseEntity<>(responseErrorObj, HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<?> loadPaymentAccountByCod(String username, String codnum) throws Exception {
		AccountModel entity = new AccountModel();
		try {
			System.out.print(" loadPaymentAccountByCod + username ! codnum "+username+" "+codnum );
			entity.setUsername(username);
			entity = accountDAO.find(entity);
			boolean resultQuery = false;
			PaymentsAccountDto paymentAccountDto = null;
			
			if(entity.getPayment() != null){
				for (PaymentModel paymentEntity : entity.getPayment()) {
					if(paymentEntity.getCardPay() != null) {
						for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
							System.out.print(" loadPaymentAccountByCod  codnum "+cardEntity.getCardNumber());

							if (cardEntity.getCardNumber().equals(codnum)) {
								paymentAccountDto = paymentParser.parsePaymentAccountOpLoad(entity);
								resultQuery = true;
							}
						}
					}
				}
			}
			
			if(resultQuery == true) {
				Response<PaymentsAccountDto> responseObj = new Response<PaymentsAccountDto>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), paymentAccountDto);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			else {
				Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			Response<AccountModel> responseErrorObj = new Response<AccountModel>(0,HttpExceptionPackSend.FOUND_PAYMENT.getAction(), null);
			return new ResponseEntity<>(responseErrorObj, HttpStatus.NOT_FOUND);
		}
	}

	
	public ResponseEntity<?> updatePaymentAccountByUsername(String username, String codnumOld,PaymentDto paymentDto) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.UPDATE_PAYMENT.getAction(), entity);
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);

			if(entity != null) {
				AccountModel entityObj = paymentParser.parsePaymentAccountOpEdit(entity, paymentDto, codnumOld); 
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

	public ResponseEntity<?> deletePaymentAccountByUsername(String username, String payCodenum, String payType) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.DELETE_PAYMENT.getAction(), entity);
		try {
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setPayCodenum(payCodenum);
			paymentDto.setPayType(payType);
			
			entity.setUsername(username);
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
	
	public ResponseEntity<?> savePaymentAccountByUsername(String username, PaymentDto paymentDto) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.CREATE_PAYMENT.getAction(), entity);
		try {
				entity.setUsername(username);
				entity = accountDAO.find(entity);

				if(entity != null) {
					AccountModel entityObj = paymentParser.parsePaymentOpSave(entity, paymentDto);
					System.out.println("  accountDAO.find "+ entityObj.getPayment().size());
	
					entity = accountDAO.update(entityObj);
					return new ResponseEntity<>(responseObj, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
		}catch (MongoClientException e ) {
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	public ResponseEntity<?> blockOrUnblockPaymentAccount(String username, String codnum, String status, String typePay) throws Exception {
		AccountModel entity = new AccountModel();
		Response<AccountModel> responseObj = new Response<AccountModel>(0,HttpExceptionPackSend.UPDATE_PAYMENT.getAction(), entity);
		try {
			entity.setUsername(username);
			entity = accountDAO.find(entity);

			if(entity != null) {
				AccountModel entityObj = paymentParser.parsePaymentAccountOpBlockOrUnblock(entity, codnum, status, typePay);
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
