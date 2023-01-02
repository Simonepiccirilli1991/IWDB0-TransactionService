package com.iwbd0.saga.model.request;

public class OrdiniRequest {

	private String btAcquirente;
	private String btRicev;
	private String codiceProd;
	private Double costo;
	
	public String getBtAcquirente() {
		return btAcquirente;
	}
	public void setBtAcquirente(String btAcquirente) {
		this.btAcquirente = btAcquirente;
	}
	public String getBtRicev() {
		return btRicev;
	}
	public void setBtRicev(String btRicev) {
		this.btRicev = btRicev;
	}
	public String getCodiceProd() {
		return codiceProd;
	}
	public void setCodiceProd(String codiceProd) {
		this.codiceProd = codiceProd;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	
}
