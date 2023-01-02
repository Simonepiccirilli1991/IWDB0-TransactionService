package com.iwbd0.saga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ordini {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String btAcquirente;
	private String btRicev;
	private String codiceProd;
	private Double costo;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
