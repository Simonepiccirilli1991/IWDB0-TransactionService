package com.iwbd0.util;

import org.springframework.stereotype.Component;

import com.iwbd0.model.response.BaseResponse;

@Component
public class CommonUtils {

	
	public BaseResponse returnError(String erko, String errMsg) {
		
		BaseResponse response = new BaseResponse();
		response.setError(true);
		response.setCodiceEsito(erko);
		response.setErrDsc(errMsg);
		return response;
	}
}
