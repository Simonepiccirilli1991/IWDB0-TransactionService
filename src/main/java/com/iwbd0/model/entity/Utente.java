package com.iwbd0.model.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "utente", uniqueConstraints=
@UniqueConstraint(columnNames={"bt"}))
@PrimaryKeyJoinColumn(name="utente_bt")
public class Utente {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String bt;
	private String username;
	private String cf;
	private String channel;
	@OneToOne(mappedBy = "utente", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Account account;

	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
