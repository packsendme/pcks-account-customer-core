package com.packsendme.microservice.account.dao;

import java.util.List;

import com.packsendme.microservice.account.repository.AccountModel;


public interface IAccountMongo {

	public AccountModel add(AccountModel entity);

	public AccountModel find(AccountModel account);
	
	public List<AccountModel> findAll();
	
	public void remove(AccountModel entity);
	
	public AccountModel update(AccountModel entity);
	
	
		

}
