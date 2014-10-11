package com.kbit.utilities.testMain;

import com.kbit.domain.exception.file.FileNotExistsException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.filewatcher.FileAddWatcher;
import com.kbit.utilities.filewatcher.Watchable;

public class Run implements Watchable,Runnable{

	public volatile boolean isRunning=true;
	
	@Override
	public void run(){
		
		KFile file=new KFile();
		file.setValue("C:\\Temp\\Test");
		
		FileAddWatcher watcher=new FileAddWatcher();
		try {
			watcher.start(file, this);
			do {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// do nothing
				}
			} while (isRunning);
			
		} catch (FileNotExistsException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void onChange(KFile file) {
		System.out.println(file.toString());
	}
	
}
