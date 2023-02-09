package com.iwbd0.util;

public class BaseError extends RuntimeException{

	private String codiceErr;
	
	public BaseError(String codiceErr) {
		super();
		this.codiceErr = codiceErr;
	}

	public String getCodiceErr() {
		return codiceErr;
	}

	public void setCodiceErr(String codiceErr) {
		this.codiceErr = codiceErr;
	}
	
	
}
