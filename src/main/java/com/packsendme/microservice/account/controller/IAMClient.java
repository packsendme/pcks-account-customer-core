package com.packsendme.microservice.account.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="packsendme-iam-server")
public interface IAMClient {
	
	@PostMapping("/iam/identity/{username}/{password}/{dtAction}")
	ResponseEntity<?> createUser(
			@PathVariable("username") String username, 
			@PathVariable("password") String password,
			@PathVariable("dtAction") String dtAction);

}
