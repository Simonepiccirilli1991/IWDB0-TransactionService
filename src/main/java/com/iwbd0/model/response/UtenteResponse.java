package com.iwbd0.model.response;

import com.iwbd0.model.entity.Utente;

public class UtenteResponse extends BaseResponse{

	private Utente utente;
	private String bt;

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}
	
	
}
