package com.kbit.domain.exception.file;

import com.kbit.domain.types.KFile;

public class FileAlreadyExistsException extends FileException{
	public FileAlreadyExistsException(String message) {
		super(message);
	}		
	
	public FileAlreadyExistsException(KFile file) {
		super(file.getValue().getAbsolutePath());
	} 
}
 