package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.model.request.UtenteRequest;
import com.iwbd0.model.response.BaseResponse;
import com.iwbd0.model.response.UtenteResponse;
import com.iwbd0.service.utente.UtenteService;

@RestController
@RequestMapping("ut")
public class UtenteController {

	@Autowired
	UtenteService utService;
	
	@RequestMapping("inserts")
	public UtenteResponse insertUtente(@RequestBody UtenteRequest request) {
		
		return utService.insertUtente(request);
	}
	
	@RequestMapping("get/{bt}")
	public UtenteResponse getInfoUtente(@PathVariable String bt) {
		
		return utService.getInfoUtente(bt);
	}
}
