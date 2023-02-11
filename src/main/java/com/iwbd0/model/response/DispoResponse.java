package com.iwbd0.model.response;

import com.iwbd0.model.entity.Account;

public class DispoResponse extends BaseResponse{

	
	private Boolean transactionOk;
	//usata per get info account
	private Account account;
	
	public Boolean getTransactionOk() {
		return transactionOk;
	}
	public void setTransactionOk(Boolean transactionOk) {
		this.transactionOk = transactionOk;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "DispoResponse [transactionOk=" + transactionOk + ", account=" + account + "]";
	}
	
	
}
