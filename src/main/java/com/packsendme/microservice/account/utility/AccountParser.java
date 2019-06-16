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
		AddressModel addressNewModel = new AddressModel();
		
		for (AddressModel addressEntity : entity.getAddress()) {
			addressNewModel = new AddressModel();
			
			if(addressEntity.getType().equals(addressDto.getType())) {
				addressNewModel.setId(addressEntity.getId());
				addressNewModel.setType(addressEntity.getId());
				addressNewModel.setAddress(addressEntity.getAddress());
				addressNewModel.setMain(accountConst.ADDRESS_ORDER_SEC); 
				addressNewModel.setType(addressEntity.getType());
			}
			else {
				addressNewModel = addressEntity;	
			}
			addressNewEntityL.add(addressNewModel);
		}
		// MEW ADDRESS ::
		addressNewModel = new AddressModel();
		addressNewModel.setType("");
		addressNewModel.setAddress(addressDto.getAddress());
		addressNewModel.setMain(accountConst.ADDRESS_ORDER_MAIN);
		addressNewModel.setType(addressDto.getType());
		addressNewEntityL.add(addressNewModel);
		
		Date dtUpdate = convertObj.convertStringToDate(addressDto.getDateUpdate());
		entity.setAddress(null);
		entity.setDateUpdate(dtUpdate);
		entity.setAddress(addressNewEntityL);
		return entity;
	}
}
