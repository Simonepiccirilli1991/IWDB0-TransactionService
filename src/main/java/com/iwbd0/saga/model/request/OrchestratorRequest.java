package com.iwbd0.saga.model.request;

public class OrchestratorRequest {

	private String btToPay;
	private String btToReceive; 
	private Double importo;
	private String codiceProdotto;
	private int trxId;
	
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
	public String getCodiceProdotto() {
		return codiceProdotto;
	}
	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	public int getTrxId() {
		return trxId;
	}
	public void setTrxId(int trxId) {
		this.trxId = trxId;
	}
	
	
	
}
