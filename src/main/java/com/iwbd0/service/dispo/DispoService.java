package com.iwbd0.service.dispo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.iwbd0.entity.repo.AccountRepo;
import com.iwbd0.entity.repo.UtenteRepo;
import com.iwbd0.model.entity.Account;
import com.iwbd0.model.entity.Utente;
import com.iwbd0.model.request.DispoRequest;
import com.iwbd0.model.response.DispoResponse;

@Service
public class DispoService {


	@Autowired
	AccountRepo accountRepo;
	@Autowired
	UtenteRepo utRepo;


	public DispoResponse inserisciDati(DispoRequest request) {

		DispoResponse response = new DispoResponse();

		Account entity = new Account();
		entity.setCodiceconto(""+request.getBtToReceive().hashCode());

		Double debito = (ObjectUtils.isEmpty(request.getDebito())) ? 0 : request.getDebito();
		entity.setDebito(debito);
		entity.setSaldoattuale(request.getImporto());
		entity.setTipoConto(request.getTipoAccount());

		Optional<Utente> ut = utRepo.findAll().stream().filter(resp -> resp.getBt().equals(request.getBtToReceive())).findAny();

		if(ObjectUtils.isEmpty(ut.get())) {

			response.setCodiceEsito("ERKO-02");
			response.setErrDsc("Utente not found");
			response.setIsError(true);
			response.setMsg("error on checking utente");
			return response;
		}

		entity.setUtente(ut.get());
		try {
			accountRepo.save(entity);

		}catch(Exception e){

			response.setCodiceEsito("ERKO-03");
			response.setErrDsc("Error on saving Account dispo");
			response.setIsError(true);
			response.setMsg("Error on saving Account dispo");
			return response;

		}

		response.setCodiceEsito("00");
		response.setMsg("Creating account OK");

		return response;

	}


	public DispoResponse infoAccount(String bt) {

		DispoResponse response = new DispoResponse();
		Account acc = null;

		Optional<Account> repoAcc = accountRepo.findAll().stream().filter(resp -> resp.getUtente().getBt().equals(bt)).findAny();

		if(ObjectUtils.isEmpty(repoAcc) || repoAcc.isEmpty()) {
			response.setCodiceEsito("ERKO-03");
			response.setErrDsc("Error on finding Account dispo");
			response.setIsError(true);
			response.setMsg("Error on finding Account dispo for this bt:"+bt);
			return response;
		}

		acc = repoAcc.get();

		response.setAccount(acc);
		response.setCodiceEsito("00");

		return response;
	}
	
	
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public DispoResponse dispoPayService(DispoRequest request) {
		DispoResponse response = new DispoResponse();

		if(ObjectUtils.isEmpty(request.getImporto()) || ObjectUtils.isEmpty(request.getBtToPay())
				|| ObjectUtils.isEmpty(request.getBtToReceive())) {
			response.setCodiceEsito("400");
			response.setIsError(true);
			response.setErrDsc("Missing parameter on request");
			return response;
		}


		Account userToPay = null;
		Account userToReceive = null;

		try {
			userToPay = accountRepo.findByUtenteBt(request.getBtToPay());
			userToReceive = accountRepo.findByUtenteBt(request.getBtToReceive());
		}catch(Exception e) {
			response.setCodiceEsito("404");
			response.setIsError(true);
			response.setErrDsc("Utente dispo conto non trovato");
			return response;
		}
		//TODO implementare gestione errore piu fica, questa fa cagare ( portarla poi su tutto il sw)
		if(ObjectUtils.isEmpty(userToPay) || ObjectUtils.isEmpty(userToReceive)) {
			response.setCodiceEsito("404");
			response.setIsError(true);
			response.setErrDsc("Utente dispo conto non trovato");
			return response;
		}

		// controllo che tipo di conto ha
		if(Boolean.TRUE.equals(userToPay.getTipoConto().equals("Debit"))) {
			// controllo che abbia i soldi o sia nel limite per debiti
			if(Boolean.TRUE.equals(makeTransactionDebit(request.getImporto(), userToPay))) {
				// ha cash quindi fare effettivo update dei 2 conti
				Double soldiConto = userToPay.getSaldoattuale();
				Double debitoConto = userToPay.getDebito();
				//caso1 ha soldi sul conto per coprire una parte
				if(soldiConto != 0) {
					//controllo se puo pagare diretto
					if(soldiConto >= request.getImporto()) {
						Double updateContoPay = soldiConto - request.getImporto();
						Double updateContoReceive = userToReceive.getSaldoattuale() + request.getImporto();
						// scalo soldi da chi paga
						//userToPay.setSaldoattuale(updateContoPay);
						accountRepo.addBalance(userToPay.getCodiceconto(), updateContoPay);
						//accountRepo.save(userToPay);
						// addo soldi a chi riceve
						//userToReceive.setSaldoattuale(updateContoReceive);
						//accountRepo.save(userToReceive);
						accountRepo.addBalance(userToReceive.getCodiceconto(), updateContoReceive);
						
						//cancellare poi
						Account print1 = accountRepo.findByUtenteBt(request.getBtToPay());
						Account print2 = accountRepo.findByUtenteBt(request.getBtToReceive());
						System.out.println(print1);
						System.out.println(print2);
					}//non puo pagare diretto
					else {
						// calcolo quanto scalare a conto e aggiungere a debito
						Double effective = request.getImporto() - soldiConto;
						debitoConto = debitoConto + effective;
						accountRepo.addBalanceDebit(request.getBtToPay(), 0, debitoConto);
						// pago chi deve riceve
						accountRepo.addBalance(request.getBtToReceive(), request.getImporto());
					}

				}//caso2 non li ha vado diretto su debito, so gia che puo farlo se arriva qui
				else {
					// calcolo quanto scalare a conto e aggiungere a debito
					Double effective = request.getImporto() - soldiConto;
					debitoConto = debitoConto + effective;
					accountRepo.addBalanceDebit(request.getBtToPay(), 0, debitoConto);
					// pago chi deve riceve
					accountRepo.addBalance(request.getBtToReceive(), request.getImporto());
				}
			}// non ha soldi e supera platform debito torno eccezione 
			else {
				response.setCodiceEsito("erko-cash");
				response.setIsError(true);
				response.setErrDsc("Operazione non possibile, controllare platform");
				response.setTransactionOk(false);
				return response;
			}
		}else {
			Double soldiConto = userToPay.getSaldoattuale();

			if(soldiConto >= request.getImporto()) {
				Double updateContoPay = soldiConto - request.getImporto();
				// pago diretto
				accountRepo.addBalance(request.getBtToPay(), updateContoPay);
				// addo a chi riceve
				accountRepo.addBalance(request.getBtToReceive(), request.getImporto());
			}else {
				response.setCodiceEsito("erko-cash");
				response.setIsError(true);
				response.setErrDsc("Operazione non possibile, controllare platform");
				response.setTransactionOk(false);
				return response;
			}
		}
		
			response.setCodiceEsito("00");
			response.setTransactionOk(true);
			return response;
		}

		private Boolean makeTransactionDebit(Double importo,Account conto ) {

			//caso 1 ci sono soldi sul conto
			if(importo <= conto.getSaldoattuale()) {
				return true;
			}
			//caso 2 non ci sono soldi su conto, ma puo andare in debito
			else if(importo > conto.getSaldoattuale() && 1000.00 > conto.getDebito()) {

				//controllo che importo non faccia superare tetto massimo
				if(conto.getDebito()+importo <= 1000.00 +conto.getSaldoattuale()) {
					return true;
				}else
					return false;

			}
			//caso 3 non dovrebbe esserci ma e venerdi e so stanco
			return false;
		}
	
}
