package com.packsendme.microservice.account.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="pcks-account-iam-core")
public interface IAMClient {
	
	@PutMapping("/account/iam/identity/{username}/{password}/{dtAction}")
	ResponseEntity<?> createUser(
			@PathVariable("username") String username, 
			@PathVariable("password") String password,
			@PathVariable("dtAction") String dtAction);

}
