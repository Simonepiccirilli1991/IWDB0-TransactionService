package com.iwbd0.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.saga.entity.OrdineAcquisti;
import com.iwbd0.saga.model.request.AcquistiRequest;
import com.iwbd0.service.saga.AcquistiService;

@RestController
@RequestMapping("order")
public class AcquistiOrderController {

	@Autowired
	AcquistiService acqService;

	@PostMapping("generate")
	public OrdineAcquisti insertOrdine(@RequestBody AcquistiRequest request) {

		return acqService.generaOrdine(request);
	}

	@RequestMapping("getAll")
	public List<OrdineAcquisti> getTuttiAcquisti(){

		return acqService.getAllOrder();
	}

	@PostMapping("update/{bt}/{status}")
	public void updateStatus(@RequestParam("bt") String bt, @RequestParam ("status") String status) {

		acqService.updateStatus(bt, status);
	}
}
