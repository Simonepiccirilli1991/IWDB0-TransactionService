package com.iwbd0.saga.model.response;

import com.iwbd0.saga.entity.Inventory;

public class InventoryResponse {

	private Double costo;
	private Boolean error;
	private Boolean allowed;
	private int maxQuantity;
	// usata per info test
	private Inventory inventory;
	
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Boolean getAllowed() {
		return allowed;
	}
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}
