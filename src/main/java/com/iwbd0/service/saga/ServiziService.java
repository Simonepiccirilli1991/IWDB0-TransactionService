package com.iwbd0.service.saga;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwbd0.saga.entity.Servizi;
import com.iwbd0.saga.entity.repo.ServiziRepo;
import com.iwbd0.saga.model.request.ServiziRequest;
import com.iwbd0.saga.model.response.ServiziResponse;

@Service
public class ServiziService {

	@Autowired
	ServiziRepo servRepo;
	
	// censisci servizi in repo
	public ServiziResponse insertService(ServiziRequest request) {
		
		ServiziResponse response = new ServiziResponse();
		
		Servizi dto = new Servizi();
		dto.setCodice(request.getCodice());
		dto.setCosto(request.getCosto());
		dto.setNomeServizio(request.getNomeServizio());
		
		try {
			response.setService(servRepo.save(dto));
		}catch(Exception e) {
			response.setError(true);
		}
		
		return response;
	}
	
	// get service
	public ServiziResponse findService(String codiceServizio) {
		
		ServiziResponse response = new ServiziResponse();
		
		List<Servizi> listService = servRepo.findAll();
		
		var iResp = listService.stream().filter(resp -> resp.getCodice().equals(codiceServizio)).findAny();
		
		if(iResp.isEmpty())
			response.setError(true);
		
		response.setService(iResp.get());
		
		return response;
	}
	
	// delete service
	public Boolean deleteService(String codiceServizio) {
		
		Boolean response;
		
		Optional<Servizi> service = Optional.of(servRepo.findByName(codiceServizio));
		
		if(service.isEmpty())
			response = false;
		else {
			servRepo.delete(service.get());
			response = true;
		}
			
		return response;
	}
}
