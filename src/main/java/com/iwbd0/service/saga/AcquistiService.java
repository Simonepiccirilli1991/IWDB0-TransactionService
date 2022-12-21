package com.iwbd0.service.saga;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwbd0.saga.entity.OrdineAcquisti;
import com.iwbd0.saga.entity.repo.AcquistiRepo;
import com.iwbd0.saga.model.request.AcquistiRequest;

import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

@Service
public class AcquistiService {

	@Autowired
	AcquistiRepo acquistiRepo;	
	@Autowired
	private Sinks.Many<AcquistiRequest> sink;


	
	// insert
	public OrdineAcquisti generaOrdine(AcquistiRequest request) {

		OrdineAcquisti response = acquistiRepo.save(requestToEnt(request));
		this.sink.tryEmitNext(request);
		return response;
	}

	// get all
	public List<OrdineAcquisti> getAllOrder(){

		List<OrdineAcquisti> response = acquistiRepo.findAll().stream().collect(Collectors.toList());

		return response;
	}
	// updateStatusOrder
	public void updateStatus(String bt, String status){

		var statusUp = acquistiRepo.findByBtAcquirente(bt);
		statusUp.setStatus(status);
		acquistiRepo.save(statusUp);

	}


	private OrdineAcquisti requestToEnt(AcquistiRequest request) {

		OrdineAcquisti response = new OrdineAcquisti();
		response.setBtVenditore(request.getBtVenditore());
		response.setBtAcquirente(request.getBtAcquirente());
		response.setCodiceProdotto(request.getCodiceProdotto());
		response.setStatus("INIT");

		return response;
	}
}
