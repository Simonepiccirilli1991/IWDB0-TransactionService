package com.iwbd0.service.utente;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Utente;
import com.iwbd0.model.request.UtenteRequest;
import com.iwbd0.model.response.UtenteResponse;


@Service
public class UtenteService {

	@Autowired
	UtenteRepo utenteRepo;
	
	Logger logger = LoggerFactory.getLogger(UtenteService.class);
	
	
	public UtenteResponse insertUtente(UtenteRequest request) {
		logger.info("API :UtenteService - insertUtente -  START with raw request: {}", request);
		
		UtenteResponse response = new UtenteResponse();
		
		Utente utente = new Utente();
		
		String bt = request.getNome() + request.getCognome().hashCode();
		
		utente.setBt(bt);
		utente.setChannel(request.getChannel());
		utente.setUsername(request.getNome().substring(0, 2)+request.getCognome());
		
		try {
		utenteRepo.save(utente);
		}
		catch(Exception e) {
			logger.error("API :UtenteService - insertUtente - EXCEPTION", e);
			return launchError("ERKO-01", "Error on inserting ut");
		}
		
		response.setBt(bt);
		response.setUsername(utente.getUsername());
		response.setCodiceEsito("00");
		
		logger.info("API :UtenteService - insertUtente - END with response: {}", response);
		return response;
		
	}
	
	public UtenteResponse getInfoUtente(String bt) {
		logger.info("API :UtenteService - getInfoUtente -  START with raw request: {}", bt);
		UtenteResponse response = new UtenteResponse();
		
		Optional<Utente> ut = utenteRepo.findAll().stream().filter(resp -> resp.getBt().equals(bt)).findAny();
		
		if(ObjectUtils.isEmpty(ut.get())) {
			return launchError("ERKO-02", "Error on finding ut");
		}
		
		
		response.setUtente(ut.get());
		response.setCodiceEsito("00");
		logger.info("API :UtenteService - getInfoUtente - END with response: {}", response);
		
		return response;
	}
	
	private UtenteResponse launchError(String erko, String errMsg) {

		UtenteResponse response = new UtenteResponse();
		response.setError(true);
		response.setCodiceEsito(erko);
		response.setErrDsc(errMsg);
		
		logger.info("API :UtenteService - insertUtente - END with response: {}", response);
		return response;
	}
}
