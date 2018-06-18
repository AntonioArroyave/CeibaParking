package com.ceiba.ceibaparking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ParqueaderoExcepcion  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ParqueaderoExcepcion(String message) {
		super(message);
	}
}
