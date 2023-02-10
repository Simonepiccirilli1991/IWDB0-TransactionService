package com.iwbd0.service.dispo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.iwbd0.entity.repo.AccountRepo;
import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Account;
import com.iwbd0.model.request.DispoRequest;
import com.iwbd0.model.response.DispoResponse;

@Service
public class DispoService {


	@Autowired
	AccountRepo accountRepo;
	@Autowired
	UtenteRepo utRepo;

	Logger logger = LoggerFactory.getLogger(DispoService.class);


	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public DispoResponse dispoPayService(DispoRequest request) {
		logger.info("API :DispoService - dispoPayService -  START with raw request: {}", request);

		DispoResponse response = new DispoResponse();

		if(ObjectUtils.isEmpty(request.getImporto()) || ObjectUtils.isEmpty(request.getBtToPay())
				|| ObjectUtils.isEmpty(request.getBtToReceive())) {
			response.setCodiceEsito("400");
			response.setError(true);
			response.setErrDsc("Missing parameter on request");
			logger.info("API :DispoService - dispoPayService - END with response: {}", response);
			return response;
		}


		Account userToPay = null;
		Account userToReceive = null;

		try {
			userToPay = accountRepo.findByUtenteBt(request.getBtToPay());
			userToReceive = accountRepo.findByUtenteBt(request.getBtToReceive());
		}catch(Exception e) {
			logger.error("API :DispoService - dispoPayService - EXCEPTION", e);
			response.setCodiceEsito("404");
			response.setError(true);
			response.setErrDsc("Utente dispo conto non trovato");
			return response;
		}
		//TODO implementare gestione errore piu fica, questa fa cagare ( portarla poi su tutto il sw)
		if(ObjectUtils.isEmpty(userToPay) || ObjectUtils.isEmpty(userToReceive)) {
			response.setCodiceEsito("404");
			response.setError(true);
			response.setErrDsc("Utente dispo conto non trovato");
			logger.info("API :DispoService - dispoPayService - END with response: {}", response);
			return response;
		}
		// controllo se puo pagare diretto
		var directTranscaction = (userToPay.getSaldoattuale() >= request.getImporto()) ? true : false;
		// paga diretto
		if(directTranscaction) {
			var updateContoPay = userToPay.getSaldoattuale() - request.getImporto();
			var updateContoReceive = userToReceive.getSaldoattuale() + request.getImporto();
			pagamentoDiretto(userToPay.getCodiceconto(), userToReceive.getCodiceconto(), updateContoPay, updateContoReceive);
		}
		else {
			//faccio debito
			var controlloDebito = (userToPay.getSaldoattuale() > 0) ? request.getImporto() - userToPay.getSaldoattuale() + userToPay.getDebito() : userToPay.getDebito() + request.getImporto();
			if(Boolean.TRUE.equals(userToPay.getTipoConto().equals("Debit")) && controlloDebito <= 1000.00) {

				pagamentoDebito(userToPay, userToReceive, request.getImporto());
			}
			else {
				response.setCodiceEsito("erko-cash");
				response.setError(true);
				response.setErrDsc("Operazione non possibile, controllare platform");
				response.setTransactionOk(false);
				logger.info("API :DispoService - dispoPayService - END with response: {}", response);
				return response;
			}
		}
		
		response.setCodiceEsito("00");
		response.setTransactionOk(true);
		logger.info("API :DispoService - dispoPayService - END with response: {}", response);
		return response;
	}		


	private void pagamentoDiretto(String contoPay, String contoReceive, Double updateContoPay, Double updateContoReceive) {

		try {
			accountRepo.addBalance(contoPay, updateContoPay);
			accountRepo.addBalance(contoReceive, updateContoReceive);

		}catch(Exception e) {
			//TODO implementare eccezzione
			logger.error("API :DispoService - dispoPayService - EXCEPTION", e);
		}
	}

	private void pagamentoDebito(Account userToPay, Account userToReceive, Double importo) {

		if(userToPay.getSaldoattuale() > 0) {

			var updateDebitoPay = (importo - userToPay.getSaldoattuale()) + userToPay.getDebito();
			// effettuo update
			try {
				accountRepo.addBalanceDebit(userToPay.getCodiceconto(), 0, updateDebitoPay);
				accountRepo.addBalance(userToReceive.getCodiceconto(), importo);
			}catch(Exception e) {
				logger.error("API :DispoService - dispoPayService - EXCEPTION", e);
			}
		}
		//2 debituo va diretto su deb
		var updateDebitoPay = importo  + userToPay.getDebito();
		// effettuo update
		try {
			accountRepo.addBalanceDebit(userToPay.getCodiceconto(), 0, updateDebitoPay);
			accountRepo.addBalance(userToReceive.getCodiceconto(), importo);
		}catch(Exception e) {
			logger.error("API :DispoService - dispoPayService - EXCEPTION", e);
		}

	}
}
