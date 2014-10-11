package com.kbit.domain.types;

import java.io.File;

public class KFile extends AbstractKObject<File> {

	@Override
	public AbstractKObject<File> setValue(File value) {
		return super.setValue(value);
	}
	
	public KFile setValue(String path){
		setValue(new File(path));
		return this;
	}
	
	public boolean exists(){
		if (isEmpty()){
			return false;
		} else {
			return value.exists();
		}
	}
	
	public boolean isFile(){
		return value==null?false:value.isFile();
	}
	
	public boolean isDirectory(){
		return value==null?false:value.isDirectory();
	}
	
	public String getPath(){
		return value==null?"":value.getPath();
	}
	

	@Override
	public String toString() {
		return getPath();
	}
	
}
