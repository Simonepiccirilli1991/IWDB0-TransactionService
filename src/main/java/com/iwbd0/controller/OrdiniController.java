package com.iwbd0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwbd0.saga.model.request.OrdiniRequest;
import com.iwbd0.saga.model.request.StatusRequest;
import com.iwbd0.saga.model.response.OrdiniResponse;
import com.iwbd0.service.saga.OrdiniService;

@RestController
@RequestMapping("ordini")
public class OrdiniController {

	@Autowired
	private OrdiniService ordServ;


	@PostMapping("insert")
	public OrdiniResponse insert(@RequestBody OrdiniRequest request) {

		return ordServ.creaOrdine(request);
	}

	@PostMapping("get")
	public OrdiniResponse getOrdini(@RequestBody OrdiniRequest request) {
		return ordServ.getOrderByBTacq(request);
	}

	@PostMapping("getall")
	public OrdiniResponse getAll(@RequestBody OrdiniRequest request) {
		return ordServ.getAll();
	}

	@PutMapping("update/{trxid}/{status}")
	public void update(@PathVariable ("trxid") String trxId, @PathVariable ("status") String status) {

		StatusRequest request = new StatusRequest();
		request.setStatus(status);
		request.setTrxId(Integer.parseInt(trxId));
		ordServ.updateStatus(request);
	}
}
