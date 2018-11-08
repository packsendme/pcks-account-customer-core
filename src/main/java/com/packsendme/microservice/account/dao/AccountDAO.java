package com.packsendme.microservice.account.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.account.repository.AccountModel;
import com.packsendme.microservice.account.repository.AccountRepository;

@Component("accountDAO")
public class AccountDAO implements IAccountMongo {
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public AccountModel add(AccountModel entity) {
		try {
			return  entity = accountRepository.insert(entity);
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AccountModel find(AccountModel entity) {
		AccountModel account = null;
		try {
			if(entity.getUsername() != null) {
				account = accountRepository.findAccountByUserName(entity.getUsername());
			}
			else if(entity.getEmail() != null) {
				account = accountRepository.findAccountByEmail(entity.getEmail());
			}
			return account;
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AccountModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(AccountModel entity) {
		try {
				accountRepository.delete(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public AccountModel update(AccountModel entity) {
		try {
			return  entity = accountRepository.save(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
