package com.iwbd0.service.saga;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwbd0.saga.entity.Ordini;
import com.iwbd0.saga.entity.repo.OrdiniRepo;
import com.iwbd0.saga.model.request.OrchestratorRequest;
import com.iwbd0.saga.model.request.OrdiniRequest;
import com.iwbd0.saga.model.request.StatusRequest;
import com.iwbd0.saga.model.response.OrchestratorResponse;
import com.iwbd0.saga.model.response.OrdiniResponse;
import com.iwbd0.service.account.AccountService;
import com.iwbd0.util.OrcClient;
import com.iwbd0.util.UtilConstatns;

@Service
public class OrdiniService {

	@Autowired
	OrdiniRepo ordiniRepo;
	@Autowired
	OrcClient orchestrator;

	Logger logger = LoggerFactory.getLogger(OrdiniService.class);

	public OrdiniResponse creaOrdine(OrdiniRequest request) {
		logger.info("API :OrdiniService - creaOrdine -  START with raw request: {}", request);

		OrdiniResponse response = new OrdiniResponse();

		Ordini enty = new Ordini();
		enty.setBtAcquirente(request.getBtAcquirente());
		enty.setBtRicev(request.getBtRicev());
		enty.setCodiceProd(request.getCodiceProd());
		enty.setCosto(request.getCosto());
		enty.setStatus(UtilConstatns.BaseWiamResponseC.STATUS_CREATED);

		Ordini iResp = ordiniRepo.save(enty);

		// setto request per avviare orchestrazione, asyc
		OrchestratorRequest oRequest = new OrchestratorRequest();
		oRequest.setBtToPay(request.getBtAcquirente());
		oRequest.setBtToReceive(request.getBtRicev());
		oRequest.setCodiceProdotto(request.getCodiceProd());
		oRequest.setImporto(request.getCosto());
		oRequest.setTrxId(iResp.getId());

		OrchestratorResponse oResponse = orchestrator.sagaOrchestration(oRequest);


		response.setOrdine(iResp);

		logger.info("API :OrdiniService - creaOrdine - END with response: {}", response);
		return response;

	}


	public OrdiniResponse getOrderById(OrdiniRequest request) {
		logger.info("API :OrdiniService - getOrderById -  START with raw request: {}", request);
		OrdiniResponse response = new OrdiniResponse();

		Optional<Ordini> iresp = ordiniRepo.findById(request.getOrderNumber());

		response.setOrdine(iresp.get());

		logger.info("API :OrdiniService - getOrderById - END with response: {}", response);
		return response;
	}

	public OrdiniResponse getAll(){

		OrdiniResponse response = new OrdiniResponse();
		response.setOrdini(new ArrayList<>());
		response.getOrdini().addAll(ordiniRepo.findAll());

		logger.info("API :OrdiniService - getAll - END with response: {}", response);
		return response;
	}

	public OrdiniResponse updateStatus(StatusRequest request) {
		logger.info("API :OrdiniService - updateStatus -  START with raw request: {}", request);

		OrdiniResponse response = new OrdiniResponse();
		Optional<Ordini> iresp = ordiniRepo.findById((long) request.getTrxId());

		if(!iresp.isEmpty()) {
			iresp.get().setStatus(request.getStatus());
			response.setOrdine(ordiniRepo.save(iresp.get()));
		}

		logger.info("API :OrdiniService - updateStatus - END with response: {}", response);
		return response;
	}
}
