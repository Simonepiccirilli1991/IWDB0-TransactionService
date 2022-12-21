package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.saga.model.request.ServiziRequest;
import com.iwbd0.saga.model.response.ServiziResponse;
import com.iwbd0.service.saga.ServiziService;

@RestController
@RequestMapping("service")
public class ServiziController {

	@Autowired
	ServiziService servServ;
	
	// insert
	@RequestMapping("insert")
	public ResponseEntity<ServiziResponse> saveService(@RequestBody ServiziRequest request){
		
		ServiziResponse response = servServ.insertService(request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// find
	@RequestMapping("find/{codiceServizio}")
	public ResponseEntity<ServiziResponse> findSerice(@PathVariable("codiceServizio") String request){
		
		ServiziResponse response = servServ.findService(request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// delete
	@RequestMapping("delete/{codiceServizio}")
	public ResponseEntity<Boolean> deleteService(@PathVariable("codiceServizio") String request){
		
		Boolean response = servServ.deleteService(request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
