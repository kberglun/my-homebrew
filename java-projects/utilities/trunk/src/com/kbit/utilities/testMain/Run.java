package com.kbit.utilities.testMain;

import com.kbit.domain.types.KFile;
import com.kbit.utilities.filewatcher.Watchable;

public class Run implements Watchable,Runnable{

	public volatile boolean isRunning=true;
	
	@Override
	public void run(){
		
		
		
	}

	@Override
	public void onChange(KFile file) {
		System.out.println(file.toString());
	}
	
}
