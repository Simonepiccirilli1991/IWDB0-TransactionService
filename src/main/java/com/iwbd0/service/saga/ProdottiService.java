package com.iwbd0.service.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.iwbd0.saga.entity.Prodotti;
import com.iwbd0.saga.entity.repo.ProdottiRepo;
import com.iwbd0.saga.model.request.ProdottiRequest;
import com.iwbd0.saga.model.response.ProdottiResponse;

@Service
public class ProdottiService {

	@Autowired
	ProdottiRepo prodRepo;
	
	public ProdottiResponse insertProdotto(ProdottiRequest request) {

		ProdottiResponse response = new ProdottiResponse();
		Prodotti enty = new Prodotti();
		enty.setAviableService(request.getAviableService());
		enty.setCodiceProdotto(request.getCodiceProdotto());
		
		response.setProdotto(prodRepo.save(enty));
		
		return response;
	}
	
	
	public ProdottiResponse transactionProd(ProdottiRequest request) {

		ProdottiResponse response = new ProdottiResponse();
		String codice = request.getCodiceProdotto();
		Prodotti prod = getByCodice(codice).getProdotto();
		int aviableServ = prod.getAviableService();
		if(aviableServ > 0) {
			prod.setAviableService(aviableServ - 1);
			prodRepo.save(prod);
			response.setTransaction(true);
		}
		else 
			response.setTransaction(false);

		return response;
	}
	
	public ProdottiResponse rollbackProd(ProdottiRequest request) {

		ProdottiResponse response = new ProdottiResponse();

		Prodotti prod = prodRepo.findByCodiceProdotto(request.getCodiceProdotto());
		int aviableServ = prod.getAviableService();
		if(!ObjectUtils.isEmpty(prod)) {
			prod.setAviableService(aviableServ + 1);
			prodRepo.save(prod);
			response.setTransaction(true);
		}
		else 
			response.setTransaction(false);

		return response;
	}
	
	public ProdottiResponse getByCodice(String codice) {

		ProdottiResponse response = new ProdottiResponse();
		Prodotti prod = prodRepo.findByCodiceProdotto(codice);

		response.setProdotto(prod);

		return response;


	}
}
