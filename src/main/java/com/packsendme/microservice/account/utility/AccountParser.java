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
			System.out.println(" 1 -  addressNewModel ");
			if(addressEntity.getType() == addressDto.getType()) {
				System.out.println(" 2 -  addressNewModel "+ addressEntity.getType());
				addressNewModel = addressEntity;
				addressNewModel.setMain(accountConst.ADDRESS_ORDER_SEC); 
			}
			else {
				System.out.println(" 3 -  addressNewModel "+ addressEntity.getType());
				addressNewModel = addressEntity;	
			}
			addressNewEntityL.add(addressNewModel);
		}
		System.out.println(" 4 -  addressNewModel "+ addressNewEntityL.size());
		// MEW ADDRESS ::
		addressNewModel.setType("");
		addressNewModel.setAddress(addressDto.getAddress());
		addressNewModel.setMain(accountConst.ADDRESS_ORDER_MAIN);
		addressNewModel.setType(addressDto.getType());
		addressNewEntityL.add(addressNewModel);
		System.out.println(" 5 -  addressNewModel "+ addressNewEntityL.size());
		
		Date dtUpdate = convertObj.convertStringToDate(addressDto.getDateUpdate());
		entity.setAddress(null);
		entity.setDateUpdate(dtUpdate);
		entity.setAddress(addressNewEntityL);
		return entity;
	}

}
