package com.packsendme.microservice.account.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="packsendme-iam-server")
public interface IAMClient {
	
	@RequestMapping(method=RequestMethod.POST, value="/iam/api/access/user/create/{username}/{password}", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> createUserAccess(@PathVariable("username") String username,  @PathVariable("password") String password);

	@RequestMapping(method=RequestMethod.POST, value="/iam/api/access/user/delete/{username}", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> deleteUserAccess(@PathVariable("username") String username);


}
