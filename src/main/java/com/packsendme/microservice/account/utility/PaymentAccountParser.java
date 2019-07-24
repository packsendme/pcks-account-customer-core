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
			if(paymentEntity.getCardPayL() != null) {
				for (CardPayModel cardEntity : paymentEntity.getCardPayL()) {
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
			if(paymentEntity.getVoucherPayL() != null) {
				for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPayL()) {
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
			if(paymentEntity.getPromotionPayL() != null) {
				for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPayL()) {
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
				if(paymentEntity.getVoucherPayL() != null  && voucherL.size() == 0)  {
					System.out.println(" VERSION 0001 VOUCHER_PAY ");
					voucherL.addAll(paymentEntity.getVoucherPayL());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
				if(paymentEntity.getPromotionPayL() != null  && promotionL.size() == 0)  {
					System.out.println(" VERSION 0001 PROMOTION_PAY ");
					promotionL.addAll(paymentEntity.getPromotionPayL());
				}
			}
			if(!paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
				if(paymentEntity.getCardPayL() != null  && cardL.size() == 0)  {
					System.out.println(" VERSION 0001 CARD_PAY ");
					cardL.addAll(paymentEntity.getCardPayL());
				}
			}
		}

		if(paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPayL() != null)  {
					for (CardPayModel cardEntity : paymentEntity.getCardPayL()) {
						if(cardEntity.getCardNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0002 cardPayObj "+ paymentDto.getPayCodenum());
							cardPayObj = new CardPayModel();
							cardPayObj.setCardName(paymentDto.getPayName());
							cardPayObj.setCardNumber(paymentDto.getPayCodenum());
							cardPayObj.setCardExpiry(paymentDto.getPayExpiry());
							cardPayObj.setCardCVV(paymentDto.getPayValue());
							cardPayObj.setCardType(paymentDto.getPayGeneralType());
							cardPayObj.setCardStatus(paymentDto.getPayStatus());
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
				if(paymentEntity.getVoucherPayL() != null)  {
					for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPayL()) {
						if(voucherEntity.getVoucherNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0003 voucherPayObj "+ paymentDto.getPayCodenum());
							voucherPayObj = new VoucherPayModel();
							voucherPayObj.setVoucherName(paymentDto.getPayName());
							voucherPayObj.setVoucherNumber(paymentDto.getPayCodenum());
							voucherPayObj.setVoucherExpiry(paymentDto.getPayExpiry());
							voucherPayObj.setVoucherValue(paymentDto.getPayValue());
							voucherPayObj.setVoucherDescription(paymentDto.getPayGeneralType());
							voucherPayObj.setVoucherStatus(paymentDto.getPayStatus());
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
				if(paymentEntity.getPromotionPayL() != null)  {
					for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPayL()) {
						if(promotionEntity.getPromotionNumber().equals(codnumOld)) {
							System.out.println(" VERSION 0004 cardPayObj "+ paymentDto.getPayCodenum());
							promotionPayObj = new PromotionPayModel();
							promotionPayObj.setPromotionName(paymentDto.getPayName());
							promotionPayObj.setPromotionNumber(paymentDto.getPayCodenum());
							promotionPayObj.setPromotionExpiry(paymentDto.getPayExpiry());
							promotionPayObj.setPromotionValue(paymentDto.getPayValue());
							promotionPayObj.setPromotionDescription(paymentDto.getPayGeneralType());
							promotionPayObj.setPromotionStatus(paymentDto.getPayStatus());
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

		Date dtUpdate = convertObj.convertStringToDate(paymentDto.getDateUpdate());
		List<CardPayModel> cardL = new ArrayList<CardPayModel>();
		List<VoucherPayModel> voucherL = new ArrayList<VoucherPayModel>();
		List<PromotionPayModel> promotionL = new ArrayList<PromotionPayModel>();
		PaymentModel paymentModel = new PaymentModel();
		List<PaymentModel> paymentL = new ArrayList<PaymentModel>();

		CardPayModel cardPayObj = null;
		VoucherPayModel voucherPayObj = null;
		PromotionPayModel promotionPayObj = null;
		

		for (PaymentModel paymentEntity : entity.getPayment()) {

			if(paymentDto.getPayType() != PaymentConstants.VOUCHER_PAY) {
				if(paymentEntity.getVoucherPayL() != null  && voucherL.size() == 0)  {
					voucherL.addAll(paymentEntity.getVoucherPayL());
				}
			}
			if(paymentDto.getPayType() != PaymentConstants.PROMOTION_PAY) {
				if(paymentEntity.getPromotionPayL() != null  && promotionL.size() == 0)  {
					promotionL.addAll(paymentEntity.getPromotionPayL());
				}
			}
			if(paymentDto.getPayType() != PaymentConstants.CARD_PAY) {
				if(paymentEntity.getCardPayL() != null  && cardL.size() == 0)  {
					cardL.addAll(paymentEntity.getCardPayL());
				}
			}
		}

		if(paymentDto.getPayType().equals(PaymentConstants.CARD_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getCardPayL() != null)  {
					
					for (CardPayModel cardEntity : paymentEntity.getCardPayL()) {
						if(!cardEntity.getCardNumber().equals(paymentDto.getPayCodenum())) {
							System.out.println(" VERSION 0003 cardPayObj "+ paymentDto.getPayCodenum());
							cardPayObj = new CardPayModel();
							cardPayObj.setCardName(paymentDto.getPayName());
							cardPayObj.setCardNumber(paymentDto.getPayCodenum());
							cardPayObj.setCardExpiry(paymentDto.getPayExpiry());
							cardPayObj.setCardCVV(paymentDto.getPayValue());
							cardPayObj.setCardType(paymentDto.getPayGeneralType());
							cardPayObj.setCardStatus(paymentDto.getPayStatus());
							cardPayObj.setDateUpdate(dtUpdate);
							cardL.add(cardPayObj);
							System.out.println(" VERSION 0004 cardL "+ cardL.size());
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.VOUCHER_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getVoucherPayL() != null)  {
					for (VoucherPayModel voucherEntity : paymentEntity.getVoucherPayL()) {
						if(!voucherEntity.getVoucherNumber().equals(paymentDto.getPayCodenum())) {
							System.out.println(" VERSION 0003 cardPayObj "+ paymentDto.getPayCodenum());
							voucherPayObj = new VoucherPayModel();
							voucherPayObj.setVoucherName(paymentDto.getPayName());
							voucherPayObj.setVoucherNumber(paymentDto.getPayCodenum());
							voucherPayObj.setVoucherExpiry(paymentDto.getPayExpiry());
							voucherPayObj.setVoucherValue(paymentDto.getPayValue());
							voucherPayObj.setVoucherDescription(paymentDto.getPayGeneralType());
							voucherPayObj.setVoucherStatus(paymentDto.getPayStatus());
							voucherPayObj.setDateUpdate(dtUpdate);
							voucherL.add(voucherPayObj);
							System.out.println(" VERSION 0004 cardL "+ cardL.size());
						}
					}
				}
			}
		}
		else if(paymentDto.getPayType().equals(PaymentConstants.PROMOTION_PAY)) {
			for (PaymentModel paymentEntity : entity.getPayment()) {
				if(paymentEntity.getPromotionPayL() != null)  {
					for (PromotionPayModel promotionEntity : paymentEntity.getPromotionPayL()) {
						if(!promotionEntity.getPromotionNumber().equals(paymentDto.getPayCodenum())) {
							System.out.println(" VERSION 0003 cardPayObj "+ paymentDto.getPayCodenum());
							promotionPayObj = new PromotionPayModel();
							promotionPayObj.setPromotionName(paymentDto.getPayName());
							promotionPayObj.setPromotionNumber(paymentDto.getPayCodenum());
							promotionPayObj.setPromotionExpiry(paymentDto.getPayExpiry());
							promotionPayObj.setPromotionValue(paymentDto.getPayValue());
							promotionPayObj.setPromotionDescription(paymentDto.getPayGeneralType());
							promotionPayObj.setPromotionStatus(paymentDto.getPayStatus());
							promotionPayObj.setDateUpdate(dtUpdate);
							promotionL.add(promotionPayObj);
							System.out.println(" VERSION 0004 cardL "+ cardL.size());
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
				if(paymentEntity.getCardPayL() != null)  {
					cardL.addAll(paymentEntity.getCardPayL());
				}
				if(paymentEntity.getVoucherPayL() != null)  {
					voucherL.addAll(paymentEntity.getVoucherPayL());
				}
				if(paymentEntity.getPromotionPayL() != null)  {
					promotionL.addAll(paymentEntity.getPromotionPayL());
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
