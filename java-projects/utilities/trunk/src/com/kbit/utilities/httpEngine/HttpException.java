package com.kbit.utilities.httpEngine;

import com.kbit.domain.types.KExceptionChecked;

public class HttpException extends KExceptionChecked {

	public HttpException(String message) {
		super(message);
	}
	
	public HttpException(Exception exception) {
		super(exception);
	}

}
