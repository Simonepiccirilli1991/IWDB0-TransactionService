package com.iwbd0.saga.model.response;

import com.iwbd0.saga.entity.Prodotti;

public class ProdottiResponse {

	private Prodotti prodotto;
	private Boolean transaction;
	
	public Prodotti getProdotto() {
		return prodotto;
	}
	public void setProdotto(Prodotti prodotto) {
		this.prodotto = prodotto;
	}
	public Boolean getTransaction() {
		return transaction;
	}
	public void setTransaction(Boolean transaction) {
		this.transaction = transaction;
	}
	
	
}
