package com.iwbd0.model.entity;




import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String codiceConto;
	private Double saldoattuale;
	private Double debito;
	private String tipoConto;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_bt", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Utente utente;
	
	public String getCodiceConto() {
		return codiceConto;
	}
	public void setCodiceConto(String codiceConto) {
		this.codiceConto = codiceConto;
	}
	public Double getSaldoattuale() {
		return saldoattuale;
	}
	public void setSaldoattuale(Double saldoattuale) {
		this.saldoattuale = saldoattuale;
	}
	public Double getDebito() {
		return debito;
	}
	public void setDebito(Double debito) {
		this.debito = debito;
	}
	public String getTipoConto() {
		return tipoConto;
	}
	public void setTipoConto(String tipoConto) {
		this.tipoConto = tipoConto;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
