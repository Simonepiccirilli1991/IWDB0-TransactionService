package com.iwbd0.model.request;

public class DispoRequest {

	private String btToPay;
	private String btToReceive; // usato anche come pk per creare acc
	private Double importo; // usato anche in fase di creazione per saldo
	
	// per creazione acc
	private String tipoAccount;
	private Double debito;
	
	public String getBtToPay() {
		return btToPay;
	}
	public void setBtToPay(String btToPay) {
		this.btToPay = btToPay;
	}
	public String getBtToReceive() {
		return btToReceive;
	}
	public void setBtToReceive(String btToReceive) {
		this.btToReceive = btToReceive;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getTipoAccount() {
		return tipoAccount;
	}
	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}
	public Double getDebito() {
		return debito;
	}
	public void setDebito(Double debito) {
		this.debito = debito;
	}
	
	
}
