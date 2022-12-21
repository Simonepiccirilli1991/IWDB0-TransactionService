package com.iwbd0.saga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nome;
	private String codiceservizio;
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
	public String getCodiceservizio() {
		return codiceservizio;
	}
	public void setCodiceservizio(String codiceservizio) {
		this.codiceservizio = codiceservizio;
	}
	public int getAmmontareDisponibile() {
		return ammontareDisponibile;
	}
	public void setAmmontareDisponibile(int ammontareDisponibile) {
		this.ammontareDisponibile = ammontareDisponibile;
	}
	
	
}
