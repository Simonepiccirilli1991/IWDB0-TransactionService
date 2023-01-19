package com.iwbd0.service.saga;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwbd0.saga.entity.Ordini;
import com.iwbd0.saga.entity.repo.OrdiniRepo;
import com.iwbd0.saga.model.request.OrchestratorRequest;
import com.iwbd0.saga.model.request.OrdiniRequest;
import com.iwbd0.saga.model.request.StatusRequest;
import com.iwbd0.saga.model.response.OrchestratorResponse;
import com.iwbd0.saga.model.response.OrdiniResponse;
import com.iwbd0.util.OrcClient;
import com.iwbd0.util.UtilConstatns;

@Service
public class OrdiniService {

	@Autowired
	OrdiniRepo ordiniRepo;
	@Autowired
	OrcClient orchestrator;
	
	public OrdiniResponse creaOrdine(OrdiniRequest request) {
		
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
		return response;
		
	}
	
	
	public OrdiniResponse getOrderById(OrdiniRequest request) {
		
		OrdiniResponse response = new OrdiniResponse();
		
		Optional<Ordini> iresp = ordiniRepo.findById(request.getOrderNumber());
		
		response.setOrdine(iresp.get());
		
		return response;
	}
	
	public OrdiniResponse getAll(){
		
		OrdiniResponse response = new OrdiniResponse();
		response.setOrdini(new ArrayList<>());
		response.getOrdini().addAll(ordiniRepo.findAll());
		
		return response;
	}
	
	public OrdiniResponse updateStatus(StatusRequest request) {
		
		OrdiniResponse response = new OrdiniResponse();
		Optional<Ordini> iresp = ordiniRepo.findById((long) request.getTrxId());
		
		if(!iresp.isEmpty()) {
			iresp.get().setStatus(request.getStatus());
			response.setOrdine(ordiniRepo.save(iresp.get()));
		}
		
		return response;
	}
}
