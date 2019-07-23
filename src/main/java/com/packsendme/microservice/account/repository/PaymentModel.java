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

		private List<CardPayModel> cardPayL;
		private List<VoucherPayModel> voucherPayL;
		private List<PromotionPayModel> promotionPayL;
		
		
		public List<CardPayModel> getCardPayL() {
			return cardPayL;
		}
		public void setCardPayL(List<CardPayModel> cardPayL) {
			this.cardPayL = cardPayL;
		}
		public List<VoucherPayModel> getVoucherPayL() {
			return voucherPayL;
		}
		public void setVoucherPayL(List<VoucherPayModel> voucherPayL) {
			this.voucherPayL = voucherPayL;
		}
		public List<PromotionPayModel> getPromotionPayL() {
			return promotionPayL;
		}
		public void setPromotionPayL(List<PromotionPayModel> promotionPayL) {
			this.promotionPayL = promotionPayL;
		}
		
		

		

}
