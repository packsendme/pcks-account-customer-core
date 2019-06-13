package com.packsendme.microservice.account.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.lib.utility.ConvertFormat;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.repository.AddressModel;

@Component
public class AccountParser {
	
	@Autowired
	private ConvertFormat convertObj;
	
	AccountConstant accountConst = new AccountConstant();
	
	public AccountModel parseAddressDtoToAccountModel(AccountModel entity, AddressAccountDto addressDto) throws Exception {
		
		List<AddressModel> addressNewEntityL = new ArrayList<AddressModel>();
		
		
		for (AddressModel addressEntity : entity.getAddress()) {
			AddressModel addressNewModel = new AddressModel();
			if(addressEntity.getType() == addressDto.getType()) {
				addressNewModel = addressEntity;
				addressNewModel.setMain(accountConst.ADDRESS_ORDER_SEC); 
			}
			else {
				addressNewModel = addressEntity;	
			}
			addressNewEntityL.add(addressNewModel);
		}
		Date dtUpdate = convertObj.convertStringToDate(addressDto.getDateUpdate());
		entity.setAddress(null);
		entity.setDateUpdate(dtUpdate);
		entity.setAddress(addressNewEntityL);
		return entity;
	}

}
