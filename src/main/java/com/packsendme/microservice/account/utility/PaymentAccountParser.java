package com.packsendme.microservice.account.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.cross.common.constants.generic.PaymentConstants;
import com.packsendme.cross.utility.ConvertFormat;
import com.packsendme.microservice.account.dto.PaymentDto;
import com.packsendme.microservice.account.dto.PaymentsAccountDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.repository.CardPayModel;
import com.packsendme.microservice.account.repository.PaymentModel;
import com.packsendme.microservice.account.repository.PromotionPayModel;
import com.packsendme.microservice.account.repository.VoucherPayModel;

@Component
public class PaymentAccountParser {
	
	@Autowired
	private ConvertFormat convertObj;
	
	public PaymentsAccountDto parsePaymentAccountOpLoad(AccountModel entity) throws Exception {
		
		List<PaymentDto> paymentDtoL = new ArrayList<PaymentDto>();
		PaymentDto paymentObjDto = null;
		PaymentsAccountDto paymentsAccount = new PaymentsAccountDto();
		//paymentAccountDto.setUsername(entity.getUsername());
		
		for (PaymentModel paymentEntity : entity.getPayment()) {
			if(paymentEntity.getCardPay() != null) {
				for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
					paymentObjDto = new PaymentDto();
					paymentObjDto.setPayPersonalName(cardEntity.getCardPersonalName());
					paymentObjDto.setPayType(PaymentConstants.CARD_PAY );
					paymentObjDto.setPayEntity(cardEntity.getCardEntity());
					paymentObjDto.setPayCodenum(cardEntity.getCardNumber());
					paymentObjDto.setPayValue(cardEntity.getCardCVV());
					paymentObjDto.setPayCountry(cardEntity.getCardCountry());
					paymentObjDto.setPayExpiry(cardEntity.getCardExpiry());
					paymentObjDto.setPayStatus(cardEntity.getCardStatus());
					paymentObjDto.setDateCreation(cardEntity.getDateCreation());
					paymentDtoL.add(paymentObjDto);
				}
			}
			if(paymentEntity.getVoucherPay() != null) {
				for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPay()) {
					paymentObjDto = new PaymentDto();
					paymentObjDto.setPayType(PaymentConstants.VOUCHER_PAY);
					paymentObjDto.setPayPersonalName(voucherEntity.getVoucherName());
					paymentObjDto.setPayEntity(voucherEntity.getVoucherEntity());
					paymentObjDto.setPayCodenum(voucherEntity.getVoucherNumber());
					paymentObjDto.setPayValue(voucherEntity.getVoucherValue());
					paymentObjDto.setPayExpiry(voucherEntity.getVoucherExpiry());
					paymentObjDto.setPayStatus(voucherEntity.getVoucherStatus());
					paymentObjDto.setDateCreation(voucherEntity.getDateCreation());
					paymentDtoL.add(paymentObjDto);
				}
			}
			if(paymentEntity.getPromotionPay() != null) {
				for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPay()) {
					paymentObjDto = new PaymentDto();
					paymentObjDto.setPayType(PaymentConstants.PROMOTION_PAY);
					paymentObjDto.setPayPersonalName(promotionEntity.getPromotionName());
					paymentObjDto.setPayEntity(promotionEntity.getPromotionEntity());
					paymentObjDto.setPayCodenum(promotionEntity.getPromotionNumber());
					paymentObjDto.setPayValue(promotionEntity.getPromotionValue());
					paymentObjDto.setPayExpiry(promotionEntity.getPromotionExpiry());
					paymentObjDto.setPayStatus(promotionEntity.getPromotionStatus());
					paymentObjDto.setDateCreation(promotionEntity.getDateCreation());
					paymentDtoL.add(paymentObjDto);
				}
			}
		}
		paymentsAccount.setUsername(entity.getUsername());
		paymentsAccount.setPayment(paymentDtoL);
		return paymentsAccount;
	}
	
	public AccountModel parsePaymentAccountOpEdit(AccountModel entity, PaymentDto paymentDto, String codnumOld) throws Exception {
		//Date dtUpdate = convertObj.convertStringToDate(paymentDto.getDateOperation());
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		CardPayModel cardPayObj = null;
		VoucherPayModel voucherPayObj = null;
		PromotionPayModel promotionPayObj = null;
		
		for (PaymentModel paymentEntity : entity.getPayment()) {
			if(!paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
				if(paymentEntity.getVoucherPay() != null  && voucherL.size() == 0)  {
					voucherL.addAll(paymentEntity.getVoucherPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
				if(paymentEntity.getPromotionPay() != null  && promotionL.size() == 0)  {
					promotionL.addAll(paymentEntity.getPromotionPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
				if(paymentEntity.getCardPay() != null  && cardL.size() == 0)  {
					cardL.addAll(paymentEntity.getCardPay());
				}
			}
		}

		if(paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPay() != null)  {
					for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
						if(cardEntity.getCardNumber().equals(codnumOld)) {
							cardPayObj = new CardPayModel();
							cardPayObj.setCardPersonalName(paymentDto.getPayPersonalName());
							cardPayObj.setCardNumber(paymentDto.getPayCodenum());
							cardPayObj.setCardExpiry(paymentDto.getPayExpiry());
							cardPayObj.setCardCVV(paymentDto.getPayValue());
							cardPayObj.setCardCountry(paymentDto.getPayCountry());
							cardPayObj.setCardEntity(paymentDto.getPayEntity());
							cardPayObj.setCardStatus(paymentDto.getPayStatus());
							cardPayObj.setDateCreation(cardEntity.getDateCreation());
							cardPayObj.setDateUpdate(paymentDto.getDateUpdate());
							cardL.add(cardPayObj);
							System.out.println(" VERSION 0002 cardL "+ cardL.size());
						}
						else {
							cardL.add(cardEntity);
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getVoucherPay() != null)  {
					for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPay()) {
						if(voucherEntity.getVoucherNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0003 voucherPayObj "+ paymentDto.getPayCodenum());
							voucherPayObj = new VoucherPayModel();
							voucherPayObj.setVoucherName(paymentDto.getPayPersonalName());
							voucherPayObj.setVoucherNumber(paymentDto.getPayCodenum());
							voucherPayObj.setVoucherExpiry(paymentDto.getPayExpiry());
							voucherPayObj.setVoucherValue(paymentDto.getPayValue());
							voucherPayObj.setVoucherEntity(paymentDto.getPayEntity());
							voucherPayObj.setVoucherStatus(paymentDto.getPayStatus());
							voucherPayObj.setDateCreation(voucherEntity.getDateCreation());
							voucherPayObj.setDateUpdate(paymentDto.getDateUpdate());
							voucherL.add(voucherPayObj);
							System.out.println(" VERSION 0003 voucherPayObj "+ voucherL.size());
						}
						else {
							voucherL.add(voucherEntity);
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getPromotionPay() != null)  {
					for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPay()) {
						if(promotionEntity.getPromotionNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0004 cardPayObj "+ paymentDto.getPayCodenum());
							promotionPayObj = new PromotionPayModel();
							promotionPayObj.setPromotionName(paymentDto.getPayPersonalName());
							promotionPayObj.setPromotionNumber(paymentDto.getPayCodenum());
							promotionPayObj.setPromotionExpiry(paymentDto.getPayExpiry());
							promotionPayObj.setPromotionValue(paymentDto.getPayValue());
							promotionPayObj.setPromotionEntity(paymentDto.getPayEntity());
							promotionPayObj.setPromotionStatus(paymentDto.getPayStatus());
							promotionPayObj.setDateCreation(promotionEntity.getDateCreation());
							promotionPayObj.setDateUpdate(paymentDto.getDateUpdate());
							promotionL.add(promotionPayObj);
							System.out.println(" VERSION 0004 cardL "+ promotionL.size());
						}
						else {
							promotionL.add(promotionEntity);
						}
					}
				}
			}
		}
		
		
		if(voucherL.size() >= 1) {
			paymentModel.setVoucherPayL(voucherL);
		}
		if(promotionL.size() >= 1) {
			paymentModel.setPromotionPayL(promotionL);
		}
		if(cardL.size() >= 1) {
			paymentModel.setCardPayL(cardL);
		}
		paymentL.add(paymentModel);
		entity.setPayment(paymentL);
		return entity;
	}
	
	// Utiliy to Payment DELETE Operation
	public AccountModel  parsePaymentAccountOpDelete(AccountModel entity, PaymentDto paymentDto) throws Exception {
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		for (PaymentModel paymentEntity : entity.getPayment()) {

			if(!paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
				if(paymentEntity.getVoucherPay() != null  && voucherL.size() == 0)  {
					voucherL.addAll(paymentEntity.getVoucherPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
				if(paymentEntity.getPromotionPay() != null  && promotionL.size() == 0)  {
					promotionL.addAll(paymentEntity.getPromotionPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
				if(paymentEntity.getCardPay() != null  && cardL.size() == 0)  {
					cardL.addAll(paymentEntity.getCardPay());
				}
			}
		}

		if(paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPay() != null)  {
					for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
						if(!cardEntity.getCardNumber().equals(paymentDto.getPayCodenum())) {
							cardL.add(cardEntity);
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getVoucherPay() != null)  {
					for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPay()) {
						if(!voucherEntity.getVoucherNumber().equals(paymentDto.getPayCodenum())) {
							voucherL.add(voucherEntity);
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getPromotionPay() != null)  {
					for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPay()) {
						if(!promotionEntity.getPromotionNumber().equals(paymentDto.getPayCodenum())) {
							promotionL.add(promotionEntity);
						}
					}
				}
			}
		}
		if(voucherL.size() >= 1) {
			paymentModel.setVoucherPayL(voucherL);
		}
		if(promotionL.size() >= 1) {
			paymentModel.setPromotionPayL(promotionL);
		}
		if(cardL.size() >= 1) {
			paymentModel.setCardPayL(cardL);
		}
		if(cardL.size() >= 1 || promotionL.size() >= 1 || voucherL.size() >= 1) {
			paymentL.add(paymentModel);
			entity.setPayment(paymentL);			
		}
		else {
			entity.setPayment(null);
		}
		return entity;
	}
	
	// Utiliy to Payment CREATE NEW Operation
	public AccountModel parsePaymentOpSave(AccountModel entity, PaymentDto paymentAccountDto) throws Exception {
		
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		CardPayModel cardPayObj = null;
		VoucherPayModel voucherPayObj = null;
		PromotionPayModel promotionPayObj = null;
		
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();

		
		//Date dtOperation = convertObj.convertStringToDate(paymentAccountDto.getDateOperation());

		if(entity.getPayment() != null) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPay() != null)  {
					cardL.addAll(paymentEntity.getCardPay());
				}
				if(paymentEntity.getVoucherPay() != null)  {
					voucherL.addAll(paymentEntity.getVoucherPay());
				}
				if(paymentEntity.getPromotionPay() != null)  {
					promotionL.addAll(paymentEntity.getPromotionPay());
				}
			}
		}
		
		if(paymentAccountDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			cardPayObj = new CardPayModel();
			cardPayObj.setCardPersonalName(paymentAccountDto.getPayPersonalName());
			cardPayObj.setCardCountry(paymentAccountDto.getPayCountry());
			cardPayObj.setCardNumber(paymentAccountDto.getPayCodenum());
			cardPayObj.setCardExpiry(paymentAccountDto.getPayExpiry());
			cardPayObj.setCardCVV(paymentAccountDto.getPayValue());
			cardPayObj.setCardEntity(paymentAccountDto.getPayEntity());
			cardPayObj.setCardStatus(paymentAccountDto.getPayStatus());
			cardPayObj.setDateCreation(paymentAccountDto.getDateCreation());
			cardL.add(cardPayObj);
			
		}
		else if(paymentAccountDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
			System.out.print(" VERSION 0002 "+ PaymentConstants.VOUCHER_PAY);

			voucherPayObj = new VoucherPayModel();
			voucherPayObj.setVoucherName(paymentAccountDto.getPayPersonalName());
			voucherPayObj.setVoucherNumber(paymentAccountDto.getPayCodenum());
			voucherPayObj.setVoucherExpiry(paymentAccountDto.getPayExpiry());
			voucherPayObj.setVoucherValue(paymentAccountDto.getPayValue());
			voucherPayObj.setVoucherEntity(paymentAccountDto.getPayEntity());
			voucherPayObj.setVoucherStatus(PaymentConstants.STATUS_PAY_ACTIVE);
			voucherPayObj.setDateCreation(paymentAccountDto.getDateCreation());
			voucherL.add(voucherPayObj);
		}
		else if(paymentAccountDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
			System.out.print(" VERSION 0002 "+ PaymentConstants.PROMOTION_PAY);

			promotionPayObj = new PromotionPayModel();
			promotionPayObj.setPromotionName(paymentAccountDto.getPayPersonalName());
			promotionPayObj.setPromotionNumber(paymentAccountDto.getPayCodenum());
			promotionPayObj.setPromotionExpiry(paymentAccountDto.getPayExpiry());
			promotionPayObj.setPromotionValue(paymentAccountDto.getPayValue());
			promotionPayObj.setPromotionEntity(paymentAccountDto.getPayEntity());
			promotionPayObj.setPromotionStatus(PaymentConstants.STATUS_PAY_ACTIVE);
			promotionPayObj.setDateCreation(paymentAccountDto.getDateCreation());
			promotionL.add(promotionPayObj);
		}
		
		System.out.print(" VERSION 0003 "+ cardL.size());
		
		if(cardL.size() >= 1)  {
			paymentModel.setCardPayL(cardL);
		}
		if(voucherL.size() >= 1) {
			paymentModel.setVoucherPayL(voucherL);
		}
		if(promotionL.size() >= 1) {
			paymentModel.setPromotionPayL(promotionL);
		}
		paymentL.add(paymentModel);
		
		System.out.print(" VERSION 0004 "+ paymentL.size());
		
		entity.setPayment(null);
		entity.setPayment(paymentL);
		return entity;
	}
	
	public AccountModel parsePaymentAccountOpBlockOrUnblock(AccountModel entity, String codnum, String status, String typePay) throws Exception {
		//Date dtUpdate = convertObj.convertStringToDate(paymentDto.getDateOperation());
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		for (PaymentModel paymentEntity : entity.getPayment()) {
			if(!typePay.equals(PaymentConstants.VOUCHER_PAY)) {
				if(paymentEntity.getVoucherPay() != null  && voucherL.size() == 0)  {
					voucherL.addAll(paymentEntity.getVoucherPay());
				}
			}
			if(!typePay.equals(PaymentConstants.PROMOTION_PAY)) {
				if(paymentEntity.getPromotionPay() != null  && promotionL.size() == 0)  {
					promotionL.addAll(paymentEntity.getPromotionPay());
				}
			}
			if(!typePay.equals(PaymentConstants.CARD_PAY)) {
				if(paymentEntity.getCardPay() != null  && cardL.size() == 0)  {
					cardL.addAll(paymentEntity.getCardPay());
				}
			}
		}

		if(typePay.equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPay() != null)  {
					for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
						if(cardEntity.getCardNumber().equals(codnum)) {
							cardEntity.setCardStatus(status);
							cardEntity.setDateUpdate(new Date());
							cardL.add(cardEntity);
						}
						else {
							cardL.add(cardEntity);
						}
					}
				}
			}
		}
		else if(typePay.equals(PaymentConstants.VOUCHER_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getVoucherPay() != null)  {
					for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPay()) {
						if(voucherEntity.getVoucherNumber().equals(codnum)) {
							voucherEntity.setVoucherStatus(status);
							voucherEntity.setDateUpdate(new Date());
							voucherL.add(voucherEntity);
						}
						else {
							voucherL.add(voucherEntity);
						}
					}
				}
			}
		}
		else if(typePay.equals(PaymentConstants.PROMOTION_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getPromotionPay() != null)  {
					for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPay()) {
						if(promotionEntity.getPromotionNumber().equals(codnum)) {
							promotionEntity.setPromotionStatus(status);
							promotionEntity.setDateUpdate(new Date());
							promotionL.add(promotionEntity);
						}
						else {
							promotionL.add(promotionEntity);
						}
					}
				}
			}
		}
		
		
		if(voucherL.size() >= 1) {
			paymentModel.setVoucherPayL(voucherL);
		}
		if(promotionL.size() >= 1) {
			paymentModel.setPromotionPayL(promotionL);
		}
		if(cardL.size() >= 1) {
			paymentModel.setCardPayL(cardL);
		}
		paymentL.add(paymentModel);
		entity.setPayment(paymentL);
		return entity;
	}

	

}
