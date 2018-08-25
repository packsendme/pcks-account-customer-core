package com.packsendme.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.account.model.Account;
import com.packsendme.microservice.account.model.AccountRepository;

@Service
public class AccountService {
	

	
	@Autowired
	private AccountRepository accountRep;

	public Account addAccount(Account account) {
		Account accountSave = null;
		try {
			accountSave = accountRep.save(account);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return accountSave;
	}
	
	public void deleteAccount(String username) {
		try {
			Account accountEntity = loadUserAccount(username);
			if(accountEntity != null) {
				System.out.println(" Accoun tNOT NULL ");
				accountRep.delete(accountEntity);
			}
			System.out.println(" deleteAccount "+ username);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Account loadUserAccount(String username) {
		try {
			System.out.println(" loadUserAccount "+ username);
			Account account = accountRep.findAccountByUserName(username);
			return account;
		}
		catch (MongoClientException e ) {
			e.printStackTrace();
			return null;
		}
	}


}
