package com.kbit.domain.types;

public abstract class KExceptionChecked extends Exception {

	public KExceptionChecked(final String message){
		super(message);
	}
	
	public KExceptionChecked(final Exception exception){
		super(exception);
	}
}
