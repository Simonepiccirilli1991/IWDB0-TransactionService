package com.iwbd0.saga.model.request;

import lombok.Data;

@Data
public class AccountRequest {

	private String bt;
	private Double importo; // usato anche in fase di creazione per saldo
	
	// per creazione acc
	private String tipoAccount;
	private Double debito;

	private String codiceConto;
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
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
