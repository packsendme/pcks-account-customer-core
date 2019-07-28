package com.packsendme.microservice.account.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.lib.common.constants.PaymentConstants;
import com.packsendme.lib.utility.ConvertFormat;
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
					paymentObjDto.setPayType(PaymentConstants.CARD_PAY );
					paymentObjDto.setPayName(cardEntity.getCardName());
					paymentObjDto.setPayCodenum(cardEntity.getCardNumber());
					paymentObjDto.setPayExpiry(cardEntity.getCardExpiry());
					paymentObjDto.setPayValue(cardEntity.getCardCVV());
					paymentObjDto.setPayGeneralType(cardEntity.getCardType());
					paymentObjDto.setPayStatus(cardEntity.getCardStatus());
					paymentDtoL.add(paymentObjDto);
				}
			}
			if(paymentEntity.getVoucherPay() != null) {
				for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPay()) {
					paymentObjDto = new PaymentDto();
					paymentObjDto.setPayType(PaymentConstants.VOUCHER_PAY);
					paymentObjDto.setPayName(voucherEntity.getVoucherName());
					paymentObjDto.setPayCodenum(voucherEntity.getVoucherNumber());
					paymentObjDto.setPayExpiry(voucherEntity.getVoucherExpiry());
					paymentObjDto.setPayValue(voucherEntity.getVoucherValue());
					paymentObjDto.setPayGeneralType(voucherEntity.getVoucherDescription());
					paymentObjDto.setPayStatus(voucherEntity.getVoucherStatus());
					paymentDtoL.add(paymentObjDto);
				}
			}
			if(paymentEntity.getPromotionPay() != null) {
				for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPay()) {
					paymentObjDto = new PaymentDto();
					paymentObjDto.setPayType(PaymentConstants.VOUCHER_PAY);
					paymentObjDto.setPayName(promotionEntity.getPromotionName());
					paymentObjDto.setPayCodenum(promotionEntity.getPromotionNumber());
					paymentObjDto.setPayExpiry(promotionEntity.getPromotionExpiry());
					paymentObjDto.setPayValue(promotionEntity.getPromotionValue());
					paymentObjDto.setPayGeneralType(promotionEntity.getPromotionDescription());
					paymentObjDto.setPayStatus(promotionEntity.getPromotionStatus());
					paymentDtoL.add(paymentObjDto);
				}
			}
		}
		paymentsAccount.setUsername(entity.getUsername());
		paymentsAccount.setPayment(paymentDtoL);
		return paymentsAccount;
	}
	
	public AccountModel parsePaymentAccountOpEdit(AccountModel entity, PaymentDto paymentDto, String codnumOld) throws Exception {
		Date dtUpdate = convertObj.convertStringToDate(paymentDto.getDateUpdate());
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		CardPayModel cardPayObj = null;
		VoucherPayModel voucherPayObj = null;
		PromotionPayModel promotionPayObj = null;
		
		System.out.println(" VERSION 000 codnumOld  "+ codnumOld);

		for (PaymentModel paymentEntity : entity.getPayment()) {
			System.out.println(" VERSION 0001  ");

			if(!paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
				if(paymentEntity.getVoucherPay() != null  && voucherL.size() == 0)  {
					System.out.println(" VERSION 0001 VOUCHER_PAY ");
					voucherL.addAll(paymentEntity.getVoucherPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
				if(paymentEntity.getPromotionPay() != null  && promotionL.size() == 0)  {
					System.out.println(" VERSION 0001 PROMOTION_PAY ");
					promotionL.addAll(paymentEntity.getPromotionPay());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
				if(paymentEntity.getCardPay() != null  && cardL.size() == 0)  {
					System.out.println(" VERSION 0001 CARD_PAY ");
					cardL.addAll(paymentEntity.getCardPay());
				}
			}
		}

		if(paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPay() != null)  {
					for (CardPayModel cardEntity : paymentEntity.getCardPay()) {
						if(cardEntity.getCardNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0002 cardPayObj "+ paymentDto.getPayCodenum());
							cardPayObj = new CardPayModel();
							cardPayObj.setCardName(paymentDto.getPayName());
							cardPayObj.setCardNumber(paymentDto.getPayCodenum());
							cardPayObj.setCardExpiry(paymentDto.getPayExpiry());
							cardPayObj.setCardCVV(paymentDto.getPayValue());
							cardPayObj.setCardType(paymentDto.getPayGeneralType());
							cardPayObj.setCardStatus(paymentDto.getPayStatus());
							cardPayObj.setDateCreation(cardEntity.getDateCreation());
							cardPayObj.setDateUpdate(dtUpdate);
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
							voucherPayObj.setVoucherName(paymentDto.getPayName());
							voucherPayObj.setVoucherNumber(paymentDto.getPayCodenum());
							voucherPayObj.setVoucherExpiry(paymentDto.getPayExpiry());
							voucherPayObj.setVoucherValue(paymentDto.getPayValue());
							voucherPayObj.setVoucherDescription(paymentDto.getPayGeneralType());
							voucherPayObj.setVoucherStatus(paymentDto.getPayStatus());
							voucherPayObj.setDateCreation(voucherEntity.getDateCreation());
							voucherPayObj.setDateUpdate(dtUpdate);
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
							promotionPayObj.setPromotionName(paymentDto.getPayName());
							promotionPayObj.setPromotionNumber(paymentDto.getPayCodenum());
							promotionPayObj.setPromotionExpiry(paymentDto.getPayExpiry());
							promotionPayObj.setPromotionValue(paymentDto.getPayValue());
							promotionPayObj.setPromotionDescription(paymentDto.getPayGeneralType());
							promotionPayObj.setPromotionStatus(paymentDto.getPayStatus());
							promotionPayObj.setDateCreation(promotionEntity.getDateCreation());
							promotionPayObj.setDateUpdate(dtUpdate);
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
			System.out.println(" VERSION 0005 voucherL "+ voucherL.size());

		}
		if(promotionL.size() >= 1) {
			paymentModel.setPromotionPayL(promotionL);
			System.out.println(" VERSION 0005 promotionL "+ promotionL.size());

		}
		if(cardL.size() >= 1) {
			paymentModel.setCardPayL(cardL);
			System.out.println(" VERSION 0005 cardL "+ promotionL.size());

		}
		paymentL.add(paymentModel);
		System.out.println(" VERSION 0006 paymentL "+ paymentL.size());

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

		
		Date dtCreation = convertObj.convertStringToDate(paymentAccountDto.getDateCreation());

		if(entity.getPayment() != null) {
			System.out.print(" VERSION 0001 ");
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
		
		System.out.print(" VERSION 0002 "+ paymentAccountDto.getPayType());
		
		if(paymentAccountDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			System.out.print(" VERSION 0002 "+ PaymentConstants.CARD_PAY);

			cardPayObj = new CardPayModel();
			cardPayObj.setCardName(paymentAccountDto.getPayName());
			cardPayObj.setCardNumber(paymentAccountDto.getPayCodenum());
			cardPayObj.setCardExpiry(paymentAccountDto.getPayExpiry());
			cardPayObj.setCardCVV(paymentAccountDto.getPayValue());
			cardPayObj.setCardType(paymentAccountDto.getPayGeneralType());
			cardPayObj.setCardStatus(PaymentConstants.STATUS_PAY_ACTIVE);
			cardPayObj.setDateCreation(dtCreation);
			cardL.add(cardPayObj);
			
		}
		else if(paymentAccountDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
			System.out.print(" VERSION 0002 "+ PaymentConstants.VOUCHER_PAY);

			voucherPayObj = new VoucherPayModel();
			voucherPayObj.setVoucherName(paymentAccountDto.getPayName());
			voucherPayObj.setVoucherNumber(paymentAccountDto.getPayCodenum());
			voucherPayObj.setVoucherExpiry(paymentAccountDto.getPayExpiry());
			voucherPayObj.setVoucherValue(paymentAccountDto.getPayValue());
			voucherPayObj.setVoucherDescription(paymentAccountDto.getPayGeneralType());
			voucherPayObj.setVoucherStatus(PaymentConstants.STATUS_PAY_ACTIVE);
			voucherPayObj.setDateCreation(dtCreation);
			voucherL.add(voucherPayObj);
		}
		else if(paymentAccountDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
			System.out.print(" VERSION 0002 "+ PaymentConstants.PROMOTION_PAY);

			promotionPayObj = new PromotionPayModel();
			promotionPayObj.setPromotionName(paymentAccountDto.getPayName());
			promotionPayObj.setPromotionNumber(paymentAccountDto.getPayCodenum());
			promotionPayObj.setPromotionExpiry(paymentAccountDto.getPayExpiry());
			promotionPayObj.setPromotionValue(paymentAccountDto.getPayValue());
			promotionPayObj.setPromotionDescription(paymentAccountDto.getPayGeneralType());
			promotionPayObj.setPromotionStatus(PaymentConstants.STATUS_PAY_ACTIVE);
			promotionPayObj.setDateCreation(dtCreation);
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
	

}
