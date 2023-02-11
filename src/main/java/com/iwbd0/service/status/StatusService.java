package com.iwbd0.service.status;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Utente;
import com.iwbd0.model.response.StatusResponse;
import com.iwbd0.util.UtilConstatns;

@Service
public class StatusService {
	
		@Autowired
		UtenteRepo utRepo;
		
		Logger logger = LoggerFactory.getLogger(StatusService.class);
		
		public StatusResponse getStatus(String bt) {
			logger.info("API :StatusService - getStatus -  START with raw request: {}", bt);
			StatusResponse response = new StatusResponse();
			
			Optional<Utente> utente = Optional.ofNullable(utRepo.findByBt(bt));
			
			//check 1 controllo se utente e attualmente registrato
			if(utente.isEmpty()) {
				response.setMsg("Utente not registered");
				response.setStatus(UtilConstatns.StatusResp.UTENTE_NOFOUND);
				logger.info("API :StatusService - getStatus - END with response: {}", response);
				
				return response;
			}
			//check 2 controllo che acc sia presente
			else if(ObjectUtils.isEmpty(utente.get().getAccount())) {
				response.setMsg("Account not registered");
				response.setStatus(UtilConstatns.StatusResp.ACCOUNT_NOFOUND);
				logger.info("API :StatusService - getStatus - END with response: {}", response);
				
				return response;
			}
			
			response.setMsg("All registered");
			response.setStatus(UtilConstatns.StatusResp.REGISTRATION_FOUND);
			logger.info("API :StatusService - getStatus - END with response: {}", response);
			
			return response;
		}
}
