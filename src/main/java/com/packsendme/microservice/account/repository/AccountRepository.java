package com.packsendme.microservice.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<AccountModel, String>   {
	
	@Query("{'username' :  {$eq: ?0}}")
	AccountModel findAccountByUserName(String username);

	@Query("{'email' :  {$eq: ?0}}")
	AccountModel findAccountByEmail(String email);

}
