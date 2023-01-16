package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.saga.model.request.AccountRequest;
import com.iwbd0.saga.model.response.AccountRespone;
import com.iwbd0.service.account.AccountService;

@RestController
@RequestMapping("acc")
public class AccountController {

	@Autowired
	AccountService accService;
	
	@PostMapping("insert")
	public ResponseEntity<AccountRespone> insertAcc(@RequestBody AccountRequest request){
		
		return new ResponseEntity<>(accService.insertAccount(request), HttpStatus.OK);
	}
	
	@GetMapping("get/{bt}")
	public ResponseEntity<AccountRespone> getAcc(@PathVariable ("bt") String bt){
		
		return new ResponseEntity<>(accService.getInfoAcc(bt), HttpStatus.OK);
	}
	
	@PostMapping("update")
	public ResponseEntity<AccountRespone> updateAcc(@RequestBody AccountRequest request){
		
		return new ResponseEntity<>(accService.updateAccInfo(request), HttpStatus.OK);
	}
}
