package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.saga.model.request.InventoryRequest;
import com.iwbd0.saga.model.response.InventoryResponse;
import com.iwbd0.service.saga.InventoryService;

@RestController
@RequestMapping("inventory")
public class InventoryController {

	@Autowired
	InventoryService invServ;
	
	// insert
	@RequestMapping("insert")
	public ResponseEntity<InventoryResponse> insertInventory(@RequestBody InventoryRequest request){

		InventoryResponse response = invServ.insertInventory(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// get
	@RequestMapping("get/{nomeInv}")
	public ResponseEntity<InventoryResponse> getInventory(@PathVariable("nomeInv") String nomeInv){

		InventoryResponse response = invServ.getInventory(nomeInv);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// transaction
	@RequestMapping("transaction")
	public ResponseEntity<InventoryResponse> transactInventory(@RequestBody InventoryRequest request){

		InventoryResponse response = invServ.transactionInventory(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// rollback
	@RequestMapping("rollback")
	public ResponseEntity<InventoryResponse> rollbackInventory(@RequestBody InventoryRequest request){

		InventoryResponse response = invServ.rollBackTransactionInventory(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
