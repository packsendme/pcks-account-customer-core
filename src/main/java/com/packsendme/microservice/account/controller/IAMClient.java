package com.packsendme.microservice.account.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="packsendme-iam-server")
public interface IAMClient {
	
	@RequestMapping(method=RequestMethod.PUT, value="/iam/api/enable/access/{username}/{password}", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> enableUserAccess(@PathVariable("username") String username, @PathVariable("password") String password);

	@RequestMapping(method=RequestMethod.DELETE, value="/iam/api/cancel/access/{username}", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> cancelUserAccessRegistration(@PathVariable("username") String username);


}
