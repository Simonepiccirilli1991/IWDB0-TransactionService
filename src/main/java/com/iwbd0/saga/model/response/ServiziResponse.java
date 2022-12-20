package com.iwbd0.saga.model.response;

import com.iwbd0.saga.entity.Servizi;

public class ServiziResponse {

	private Servizi service;
	private Boolean error;
	
	public Servizi getService() {
		return service;
	}

	public void setService(Servizi service) {
		this.service = service;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	
	
}
