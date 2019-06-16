package com.packsendme.microservice.account.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.lib.utility.ConvertFormat;
import com.packsendme.microservice.account.dto.PaymentAccountCRUDDto;
import com.packsendme.microservice.account.dto.PaymentAccountCollectionDto;
import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.repository.PaymentModel;

@Component
public class PaymentAccountParser {
	
	AccountConstant accountConst = new AccountConstant();
	
	@Autowired
	private ConvertFormat convertObj;
	
	public PaymentAccountCollectionDto parsePaymentAccountOperationFind(AccountModel entity) throws Exception {
		
		List<PaymentDto> paymentDtoL = new ArrayList<PaymentDto>();
		PaymentAccountCollectionDto paymentAccountDto = new PaymentAccountCollectionDto();
		PaymentDto paymentDto = new PaymentDto();
		
		paymentAccountDto.setId(entity.getId());
		paymentAccountDto.setUsername(entity.getUsername());
		
		for (PaymentModel paymentEntity : entity.getPayment()) {
			paymentDto = new PaymentDto();
			paymentDto.setId(paymentEntity.getId());
			paymentDto.setCardName(paymentEntity.getCardName());
			paymentDto.setCardNumber(paymentEntity.getCardNumber());
			paymentDto.setCardExpiry(paymentEntity.getCardExpiry());
			paymentDto.setCardCVV(paymentEntity.getCardCVV());
			paymentDto.setCardType(paymentEntity.getCardType());
			paymentDto.setCardStatus(paymentEntity.getCardStatus());
			paymentDtoL.add(paymentDto);
		}
		paymentAccountDto.setPayment(paymentDtoL);
		return paymentAccountDto;
	}
	
	// Utiliy to Payment UPDATE Operation
	public AccountModel parsePaymentAccountOperationEdit(AccountModel entity, PaymentAccountCRUDDto paymentAccountCruDto) throws Exception {
		
		List<PaymentModel> paymentModelL = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		Date dtUpdate = convertObj.convertStringToDate(paymentAccountCruDto.getDateUpdate());
		entity.setDateUpdate(dtUpdate);
		
		if(entity.getPayment() != null) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				paymentModel = new PaymentModel();
				
				if(paymentEntity.getCardNumber().equals(paymentAccountCruDto.getCardNumber())) {
					paymentModel.setId(paymentAccountCruDto.getId());
					paymentModel.setCardName(paymentAccountCruDto.getCardName());
					paymentModel.setCardNumber(paymentAccountCruDto.getCardNumber());
					paymentModel.setCardExpiry(paymentAccountCruDto.getCardExpiry());
					paymentModel.setCardCVV(paymentAccountCruDto.getCardCVV());
					paymentModel.setCardType(paymentAccountCruDto.getCardType());
					paymentModel.setCardStatus(paymentAccountCruDto.getCardStatus());
				}
				else {
					paymentModel = paymentEntity;
				}
				paymentModelL.add(paymentModel);
			}
			entity.setPayment(null);
			entity.setPayment(paymentModelL);
		}
		return entity;
	}
	
	// Utiliy to Payment DELETE Operation
	public AccountModel parsePaymentAccountOperationDelete(AccountModel entity, PaymentAccountCRUDDto paymentAccountCruDto) throws Exception {
		
		List<PaymentModel> paymentModelL = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		Date dtUpdate = convertObj.convertStringToDate(paymentAccountCruDto.getDateUpdate());
		entity.setDateUpdate(dtUpdate);
		
		if(entity.getPayment()!= null) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				paymentModel = new PaymentModel();

				if(paymentEntity.getCardNumber() != paymentAccountCruDto.getCardNumber()) {
					paymentModel = paymentEntity;
					paymentModelL.add(paymentModel);
				}
			}
		}
		entity.setPayment(null);
		entity.setPayment(paymentModelL);
		return entity;
	}
	
	// Utiliy to Payment CREATE NEW Operation
	public AccountModel parsePaymentAccountOperationSave(AccountModel entity, PaymentAccountCRUDDto paymentAccountCruDto) throws Exception {
		
		List<PaymentModel> paymentModelL = new ArrayList<PaymentModel>();
		PaymentModel paymentModel = new PaymentModel();
		Date dtUpdate = convertObj.convertStringToDate(paymentAccountCruDto.getDateUpdate());
		entity.setDateUpdate(dtUpdate);
		
		paymentModel.setId(paymentAccountCruDto.getId());
		paymentModel.setCardName(paymentAccountCruDto.getCardName());
		paymentModel.setCardNumber(paymentAccountCruDto.getCardNumber());
		paymentModel.setCardExpiry(paymentAccountCruDto.getCardExpiry());
		paymentModel.setCardCVV(paymentAccountCruDto.getCardCVV());
		paymentModel.setCardType(paymentAccountCruDto.getCardType());
		paymentModel.setCardStatus(paymentAccountCruDto.getCardStatus());

		if(entity.getPayment() != null ) {
			paymentModelL.addAll(entity.getPayment());	
		}

		paymentModelL.add(paymentModel);
		entity.setPayment(null);
		entity.setPayment(paymentModelL);
		
		return entity;
	}
	

}
