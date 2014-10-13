package com.kbit.utilities.filewatcher;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.kbit.domain.exception.file.FileCommonException;
import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.exception.file.FileNotExistsException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.log.Log;

public class FileAddWatcher {

	private final Runner runner;
	private final Thread serviceThread;
	
	public FileAddWatcher(final KFile watch, final Watchable action) throws FileException {
		if (!watch.isDirectory()) {
			throw new FileNotExistsException("File "+watch+" is not a directory!");
		}
		runner=new Runner(watch, action);
		serviceThread=new Thread(runner);
	}
	
	public void start() {
		serviceThread.start();
	}
	
	public void stop(){
		runner.isRunning=false;
	}

	private class Runner implements Runnable {

		private static final int TRIES_MAX=10;
		
		volatile boolean isRunning=true;
		final KFile watch;
		final Watchable action;
		final WatchKey key;
		final WatchService watchService;
		
		int tries=0;
		
		public Runner(final KFile watch, final Watchable action) throws FileCommonException {
			this.watch=watch;
			this.action=action;
			
			final Path path=Paths.get(watch.getValue().getPath());
			try {
				watchService=FileSystems.getDefault().newWatchService();
				key=path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			} catch (IOException e) {
				throw new FileCommonException(e);
			}
		}
		
		synchronized void stop(){
			isRunning=false;
		}
		
		@Override
		public void run() {
			while (isRunning && tries<TRIES_MAX) {
				
				WatchKey watchKey;
				try {
					Log.debug("Waiting for file in directory "+watch.getPath());
					watchKey=watchService.take();
				} catch (InterruptedException e) {
					Log.warning("Caught InterruptedException when retrieving file from WatchService: tries "+tries);
					tries++;
					continue;
				}
				
				for (final WatchEvent<?> event: watchKey.pollEvents()) {
	                WatchEvent.Kind kind = event.kind();
	                
	                // TBD - provide example of how OVERFLOW event is handled
	                if (kind == OVERFLOW) {
	                	Log.warning("Overflow when getting watch key events");
	                    continue;
	                }
	 
	                // Context for directory entry event is the file name of entry
	                WatchEvent<Path> ev = (WatchEvent<Path>) event;
	                
	                final String newFilePath=watch.getPath()+KFile.DIR_SEPARATOR+ev.context();
	                
	                Log.debug("File triggered: "+newFilePath);

	                KFile file=new KFile();
	                file.setValue(new File(newFilePath));
	                action.onChange(file);
	            }
				
	            if (!key.reset()) {
	            	Log.exeption("Directory being watched ("+watch.getPath()+")no longer accessible");
	            	isRunning=false;
	            }
			}
			if (tries==TRIES_MAX) {
				Log.exeption("Number of tries too large: "+tries);
			} 
			Log.debug("Watcher thread ended normally");
		}
		
	}
	
}
