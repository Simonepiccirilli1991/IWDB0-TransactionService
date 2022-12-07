package com.iwbd0.model.response;

import com.iwbd0.model.entity.Account;

public class DispoResponse extends BaseResponse{

	
	private Boolean transactionOk;
	private String msg;
	private Account account;
	
	public Boolean getTransactionOk() {
		return transactionOk;
	}
	public void setTransactionOk(Boolean transactionOk) {
		this.transactionOk = transactionOk;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
