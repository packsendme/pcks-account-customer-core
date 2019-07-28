package com.packsendme.microservice.account.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class PaymentModel implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */

		private List<CardPayModel> cardPay;
		private List<VoucherPayModel> voucherPay;
		private List<PromotionPayModel> promotionPay;
		
		
		public List<CardPayModel> getCardPay() {
			return cardPay;
		}
		public void setCardPayL(List<CardPayModel> cardPay) {
			this.cardPay = cardPay;
		}
		public List<VoucherPayModel> getVoucherPay() {
			return voucherPay;
		}
		public void setVoucherPayL(List<VoucherPayModel> voucherPay) {
			this.voucherPay = voucherPay;
		}
		public List<PromotionPayModel> getPromotionPay() {
			return promotionPay;
		}
		public void setPromotionPayL(List<PromotionPayModel> promotionPay) {
			this.promotionPay = promotionPay;
		}
		
		

		

}
