package com.iwbd0.saga.model.response;

import com.iwbd0.model.entity.Account;
import com.iwbd0.model.response.BaseResponse;

public class AccountRespone extends BaseResponse{

	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "AccountRespone [account=" + account + "]";
	}
	
	
}
