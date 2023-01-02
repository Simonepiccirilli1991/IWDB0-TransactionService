package com.iwbd0.saga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Prodotti {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String codiceProdotto;
	private int aviableService;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodiceProdotto() {
		return codiceProdotto;
	}
	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	public int getAviableService() {
		return aviableService;
	}
	public void setAviableService(int aviableService) {
		this.aviableService = aviableService;
	}
	
	
}
