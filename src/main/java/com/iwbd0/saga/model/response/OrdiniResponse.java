package com.iwbd0.saga.model.response;

import java.util.List;

import com.iwbd0.saga.entity.Ordini;

public class OrdiniResponse {

	private Ordini ordine;
	private List<Ordini> ordini;
	
	public Ordini getOrdine() {
		return ordine;
	}
	public void setOrdine(Ordini ordine) {
		this.ordine = ordine;
	}
	public List<Ordini> getOrdini() {
		return ordini;
	}
	public void setOrdini(List<Ordini> ordini) {
		this.ordini = ordini;
	}
	
	
}
