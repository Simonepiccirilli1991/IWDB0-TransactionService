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

import com.iwbd0.saga.model.request.ProdottiRequest;
import com.iwbd0.saga.model.response.ProdottiResponse;
import com.iwbd0.service.saga.ProdottiService;

@RestController
@RequestMapping("prodotti")
public class ProdottiController {

	@Autowired
	private ProdottiService prodServ;
	
	
	@RequestMapping("insert")
	public ProdottiResponse insert(@RequestBody ProdottiRequest request) {
		
		return prodServ.insertProdotto(request);
	}
	
	@PostMapping("transact")
	public ResponseEntity<ProdottiResponse> transaction(@RequestBody ProdottiRequest request) {
		
		return  new ResponseEntity<>(prodServ.transactionProd(request), HttpStatus.OK);
	}
	
	@PostMapping("rollb")
	public ProdottiResponse rollback(@RequestBody ProdottiRequest request) {
		
		return prodServ.rollbackProd(request);
	}
	
	@GetMapping("get/{codice}")
	public ProdottiResponse get(@PathVariable String codice) {
		
		return prodServ.getByCodice(codice);
	}
}
