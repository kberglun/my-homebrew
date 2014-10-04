package com.kbit.domain.types;

public abstract class KExceptionUnchecked extends RuntimeException {

	public KExceptionUnchecked(final String message){
		super(message);
	}
	
	public KExceptionUnchecked(final Exception exception){
		super(exception);
	}
}
