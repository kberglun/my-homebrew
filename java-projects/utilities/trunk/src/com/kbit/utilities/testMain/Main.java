package com.kbit.utilities.testMain;

import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.filewatcher.FileAddWatcher;


public class Main {

	public static void main(String[] args) {
		
		KFile file=new KFile();
		file.setValue("C:\\Temp\\Test");
		
		try {
			FileAddWatcher watcher=new FileAddWatcher(file,null);
			watcher.start();
			
		} catch (FileException e1) {
			e1.printStackTrace();
		}
		

	}
	
	
	

}
