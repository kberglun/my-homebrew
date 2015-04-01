package com.kbit.domain.types;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.kbit.domain.exception.file.FileCommonException;
import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.exception.file.FileNotExistsException;

public class KFile extends AbstractKObject<File> {

	public static final String DIR_SEPARATOR=FileSystems.getDefault().getSeparator();
	
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
	
	public String getName(){
		return value==null?"":value.getName();
	}
	
	public Collection<KFile> getFilesInDirectory(){
		Collection<KFile> collection=new ArrayList<KFile>();
		
		if (value!=null && isDirectory()) {
			for (final File file:value.listFiles()) {
				KFile newFile=new KFile();
				newFile.setValue(file);
				collection.add(newFile);
			}
		}
		
		return collection;
	}
	
	public String getExtension(){
		return isFile()?getName().substring(getName().lastIndexOf('.')):"";
	}
	
	public List<String> readAllLines() throws FileException{
		if (isEmpty()) {
			throw new FileNotExistsException("No file specified");
		}
		if (!exists()) {
			throw new FileNotExistsException("File "+getPath()+" does not exist");
		}
		if (!isFile()) {
			throw new FileNotExistsException("File "+getPath()+" is not a file");
		}
		
		List<String> allLines;
		
		try {
			allLines=Files.readAllLines(Paths.get(getValue().getPath()));
		} catch (IOException e) {
			throw new FileCommonException(e);
		}
		
		return allLines;
	}
	

	@Override
	public String toString() {
		return getPath();
	}

	@Override
	public String serialize() {
		return getPath();
	}

	@Override
	public void deserialize(String string) {
		setValue(string);
	}
	
}
