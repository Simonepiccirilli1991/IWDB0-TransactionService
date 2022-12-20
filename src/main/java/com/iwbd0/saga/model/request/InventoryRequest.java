package com.iwbd0.saga.model.request;

public class InventoryRequest {

	private String nome;
	private int quantita;
	// usati per popolare db
	private String codiceServizio;
	private int ammontareDisponibile;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public int getAmmontareDisponibile() {
		return ammontareDisponibile;
	}
	public void setAmmontareDisponibile(int ammontareDisponibile) {
		this.ammontareDisponibile = ammontareDisponibile;
	}
	
	
}
