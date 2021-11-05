package com.packsendme.microservice.account.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packsendme.cross.common.constants.generic.AccountConstant;
import com.packsendme.cross.utility.ConvertFormat;
import com.packsendme.microservice.account.dto.AccountLoadDto;
import com.packsendme.microservice.account.dto.AddressAccountDto;
import com.packsendme.microservice.account.dto.AddressDto;
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
		
		if(entity.getAddress() != null) {
			for (AddressModel addressEntity : entity.getAddress()) {
				addressNewModel = new AddressModel();
					
				if(addressEntity.getType().equals(addressDto.getType())) {
					addressNewModel.setId(addressEntity.getId());
					addressNewModel.setAddress(addressEntity.getAddress());
					addressNewModel.setMain(accountConst.ADDRESS_ORDER_SEC);
					addressNewModel.setCity(addressEntity.getCity());
					addressNewModel.setCountry(addressEntity.getCountry());
					addressNewModel.setType(addressEntity.getType());
				}
				else {
					addressNewModel = addressEntity;	
				}
				addressNewEntityL.add(addressNewModel);
			}
		}
		// MEW ADDRESS ::
		addressNewModel = new AddressModel();
		addressNewModel.setType("");
		addressNewModel.setAddress(addressDto.getAddress());
		addressNewModel.setMain(accountConst.ADDRESS_ORDER_MAIN);
		addressNewModel.setType(addressDto.getType());
		addressNewModel.setCity(addressDto.getCity());
		addressNewModel.setCountry(addressDto.getCountry());

		addressNewEntityL.add(addressNewModel);
		
		Date dtUpdate = convertObj.convertStringToDate(addressDto.getDateOperation());
		entity.setAddress(null);
		entity.setDateUpdate(dtUpdate);
		entity.setAddress(addressNewEntityL);
		return entity;
	}
	
	public AccountLoadDto parseAccountModelToAccountLoad(AccountModel entity) {
		
		List<AddressDto> addressDtoL = new ArrayList<AddressDto>();
		AccountLoadDto accountLoad = new AccountLoadDto();
		AddressDto addressDto = new AddressDto();
		
		accountLoad.setUsername(entity.getUsername());
		accountLoad.setEmail(entity.getEmail());
		accountLoad.setName(entity.getName());
		accountLoad.setLastName(entity.getLastName());
		accountLoad.setCountry(entity.getCountry());
		accountLoad.setDateCreation(entity.getDateCreation());
		accountLoad.setDateUpdate(entity.getDateUpdate());
		
		if(entity.getAddress() != null) {
			for (AddressModel addressEntity : entity.getAddress()) {
				addressDto = new AddressDto();
				addressDto.setId(addressEntity.getId());
				addressDto.setType(addressEntity.getId());
				addressDto.setAddress(addressEntity.getAddress());
				addressDto.setMain(addressEntity.getMain()); 
				addressDto.setType(addressEntity.getType());
				addressDto.setCity(addressEntity.getCity());
				addressDto.setCountry(addressEntity.getCountry());
				addressDtoL.add(addressDto);
			}
		}
		// MEW ADDRESS ::
		accountLoad.setAddress(addressDtoL);
		return accountLoad;
	}
}
