package com.iwbd0.saga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Servizi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nomeServizio;
	private Double costo;
	private String codice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeServizio() {
		return nomeServizio;
	}
	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	
}
