package com.kbit.domain.exception.file;

import com.kbit.domain.types.KExceptionChecked;

public abstract class FileException extends KExceptionChecked {

	public FileException(Exception exception) {
		super(exception);
	}

	public FileException(String message) {
		super(message);
	}
	
}
