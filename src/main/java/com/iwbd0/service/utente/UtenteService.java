package com.iwbd0.service.utente;

import java.util.Optional;

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
	
	
	public UtenteResponse insertUtente(UtenteRequest request) {
		
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
			response.setMsg(e.getMessage());
			response.setCodiceEsito("ERKO-01");
			response.setIsError(false);
			return response;
		}
		
		response.setBt(bt);
		response.setUsername(utente.getUsername());
		response.setCodiceEsito("00");
		return response;
		
	}
	
	public UtenteResponse getInfoUtente(String bt) {
		
		UtenteResponse response = new UtenteResponse();
		
		Optional<Utente> ut = utenteRepo.findAll().stream().filter(resp -> resp.getBt().equals(bt)).findAny();
		
		if(ObjectUtils.isEmpty(ut.get())) {
			response.setCodiceEsito("ERKO-02");
			response.setErrDsc("Utente not found");
			response.setIsError(true);
			response.setMsg("error on checking utente");
			return response;
		}
		
		
		response.setUtente(ut.get());
		response.setCodiceEsito("00");
		
		return response;
	}
	
}
