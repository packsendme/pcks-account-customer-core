package com.packsendme.microservice.account.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<Account, String>   {
	
	@Query("{'user.email': ?0}")
	public Account findAllByUserName(String email);
	
	public Account addUser(String email);

	public Account cancelUserByStatus(String email);
	
	public Account updateAccount(Account account);

}
