package com.iwbd0.model.response;

public class BaseResponse {

	
	private String msg;
	private String codiceEsito;
	private Boolean isError;
	private String errDsc;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCodiceEsito() {
		return codiceEsito;
	}
	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}
	public String getErrDsc() {
		return errDsc;
	}
	public void setErrDsc(String errDsc) {
		this.errDsc = errDsc;
	}
	
	
	
	
}
