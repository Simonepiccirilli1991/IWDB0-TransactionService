package com.iwbd0.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iwbd0.util.BaseError;
import com.iwbd0.util.ErrorDto;



@RestControllerAdvice
public class ExeptionController {

	public static final String LOGIC_PREFIX = "iwdb0.logic.";
	private static final String GENERIC = "iwdb0.generic";
	
	@ExceptionHandler(BaseError.class)
	public ResponseEntity<ErrorDto> actionError(BaseError ex){
		
		ErrorDto response = new ErrorDto();
		ErrorMapperDTO dto = errorMapper(ex.getCodiceErr());
		response.setCause(dto.getCause());
		response.setMessage(dto.getMessage());
		response.setStatus(dto.getHttpStatus());
		return new ResponseEntity<>(response, dto.getHttpStatus());
	}
	
	
	
	
	private static ErrorMapperDTO errorMapper(String errId) {
		if(errId == null) errId = "EMPTY";
		String message = null;
		String logicErrId = null;
		String cause = null;
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;

		switch (errId) {
			case "ERKO-02": message = "ERKO-02";    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; logicErrId = "Generic error";                    break;
			case "ERKO-03": 
			case "ERKO-04":	message = "ERKO-04" ;	httpStatus = HttpStatus.UNAUTHORIZED;	       logicErrId = "Invalid data provided";			break;
			
			default:
				cause = "Generic Error";
				message = "ERKO-01";
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				break;

			
		}

		if(logicErrId != null) cause = LOGIC_PREFIX +"_"+ logicErrId;

		return new ErrorMapperDTO(message, cause, httpStatus);
	}
}
class ErrorMapperDTO {
	private final String message;
	private final String cause;
	private final HttpStatus httpStatus;
	
	public ErrorMapperDTO(String message, String cause, HttpStatus httpStatus) {
		this.message = message;
		this.cause = cause;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getCause() {
		return cause;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	
}
