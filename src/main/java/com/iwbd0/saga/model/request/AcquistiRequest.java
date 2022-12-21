package com.iwbd0.saga.model.request;

public class AcquistiRequest {

	private String btAcquirente;
	private String btVenditore;
	private String codiceProdotto;
	
	public String getBtAcquirente() {
		return btAcquirente;
	}
	public void setBtAcquirente(String btAcquirente) {
		this.btAcquirente = btAcquirente;
	}
	public String getBtVenditore() {
		return btVenditore;
	}
	public void setBtVenditore(String btVenditore) {
		this.btVenditore = btVenditore;
	}
	public String getCodiceProdotto() {
		return codiceProdotto;
	}
	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	
	
}
