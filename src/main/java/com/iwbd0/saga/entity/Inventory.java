package com.iwbd0.saga.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String nome;
	private String codiceServizio;
	private int ammontareDisponibile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
