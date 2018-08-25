package com.packsendme.microservice.account.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<Account, String>   {
	
	@Query("{'userName' :  {$eq: ?0}}")
	Account findAccountByUserName(String username);

}
