package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.model.request.DispoRequest;
import com.iwbd0.model.response.DispoResponse;
import com.iwbd0.service.dispo.DispoService;

@RestController
@RequestMapping("dispo")
public class DispoController {

	
	@Autowired
	DispoService dispoService;
	
	
	@RequestMapping("transaction")
	DispoResponse maketransaction(@RequestBody DispoRequest request) {
		
		return dispoService.dispoPayService(request);
		
		
	}
}
