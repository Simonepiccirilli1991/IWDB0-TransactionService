package com.iwbd0.service.account;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.iwbd0.entity.repo.AccountRepo;
import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Account;
import com.iwbd0.model.entity.Utente;
import com.iwbd0.saga.model.request.AccountRequest;
import com.iwbd0.saga.model.response.AccountRespone;
import com.iwbd0.util.BaseError;
import com.iwbd0.util.CommonUtils;

@Service
public class AccountService {

	@Autowired
	AccountRepo accRepo;
	@Autowired
	UtenteRepo utRepo;
	@Autowired
	CommonUtils util;
	
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	// insert Acc
	public AccountRespone insertAccount(AccountRequest request) {
		logger.info("API :AccountService - insertAccount -  START with raw request: {}", request);
		
		AccountRespone response = new AccountRespone();
		Account entity = new Account();
		entity.setCodiceconto(""+request.getBt().hashCode());

		Double debito = (ObjectUtils.isEmpty(request.getDebito())) ? 0 : request.getDebito();
		entity.setDebito(debito);
		entity.setSaldoattuale(request.getImporto());
		entity.setTipoConto(request.getTipoAccount());

		Optional<Utente> ut = utRepo.findAll().stream().filter(resp -> resp.getBt().equals(request.getBt())).findAny();

		if(ut.isEmpty()) {
		return launchError("ERKO-01", "Error on inserting acc");
		}

		entity.setUtente(ut.get());
		try {
			accRepo.save(entity);

		}catch(Exception e){
			logger.error("API :AccountService - insertAccount - EXCEPTION", e);
			response.setCodiceEsito("ERKO-03");
			response.setErrDsc("Error on saving Account dispo");
			response.setError(true);
			response.setMsg("Error on saving Account dispo");
			return response;

		}

		response.setCodiceEsito("00");
		response.setMsg("Creating account OK");
		
		logger.info("API :AccountService - insertAccount - END with response: {}", response);
		
		return response;
	}
	
	
	// getInfoAccount
	public AccountRespone getInfoAcc(String bt) {
		logger.info("API :AccountService - getInfoAcc -  START with raw request: {}", bt);
		
		AccountRespone response = new AccountRespone();
		Account acc = null;

		Optional<Account> repoAcc = accRepo.findAll().stream().filter(resp -> resp.getUtente().getBt().equals(bt)).findAny();

		if(ObjectUtils.isEmpty(repoAcc) || repoAcc.isEmpty()) {
			
			logger.info("API :AccountService - getInfoAcc - END with response: {}", response);
			throw new BaseError("ERKO-02");
		}

		acc = repoAcc.get();

		response.setAccount(acc);
		response.setCodiceEsito("00");
		logger.info("API :AccountService - getInfoAcc - END with response: {}", response);
		
		return response;
	}
	
	//updateAccInfo
	public AccountRespone updateAccInfo(AccountRequest request) {
		logger.info("API :AccountService - updateAccInfo -  START with raw request: {}", request);
		
		AccountRespone response = new AccountRespone();
		Account acc = null;

		Optional<Account> repoAcc = accRepo.findAll().stream().filter(resp -> resp.getUtente().getBt().equals(request.getBt())).findAny();

		if(ObjectUtils.isEmpty(repoAcc) || repoAcc.isEmpty()) {
			response.setCodiceEsito("ERKO-03");
			response.setErrDsc("Error on finding Account dispo");
			response.setError(true);
			response.setMsg("Error on finding Account dispo for this bt:"+request.getBt());
			logger.info("API :AccountService - updateAccInfo - END with response: {}", response);
			
			return response;
		}

		acc = repoAcc.get();
		
		if(!ObjectUtils.isEmpty(request.getDebito()))
			acc.setDebito(request.getDebito());
		
		acc.setSaldoattuale(request.getImporto());
		acc.setTipoConto(request.getTipoAccount());
		
		try {
			response.setAccount(accRepo.save(acc));
		}catch(Exception e) {
			logger.error("API :AccountService - updateAccInfo - EXCEPTION", e);
			response.setCodiceEsito("ERKO-02");
			response.setErrDsc("Error on updating Account dispo");
			response.setError(true);
			response.setMsg("Error on updating Account dispo for this bt:"+request.getBt());
			return response;
		}
		
		response.setCodiceEsito("00");
		logger.info("API :AccountService - updateAccInfo - END with response: {}", response);
		
		return response;
	}
	
	
	private AccountRespone launchError(String erko, String errMsg) {
		
		AccountRespone response = new AccountRespone();
		response.setError(true);
		response.setCodiceEsito(erko);
		response.setErrDsc(errMsg);
		
		return response;
	}
}
