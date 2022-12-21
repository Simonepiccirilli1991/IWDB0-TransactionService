package com.iwbd0.saga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "acquisti")
public class OrdineAcquisti {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String btAcquirente;
	private String btVenditore;
	private String codiceProdotto;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
