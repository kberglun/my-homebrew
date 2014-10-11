package com.kbit.utilities.filewatcher;

import java.util.Collection;
import java.util.HashSet;

import javax.swing.Timer;

import com.kbit.domain.exception.file.FileNotExistsException;
import com.kbit.domain.types.KFile;

public class FileAddWatcher {

	private int timeoutMS=5000;
	private Timer timer;
	
	/**
	 * Set timer timeout in millisecond, default is 5000
	 * @param timeoutMS
	 * @return
	 */
	public FileAddWatcher setTimeout(final int timeoutMS) {
		this.timeoutMS=timeoutMS;
		return this;
	}
	
	public synchronized boolean start(final KFile watch, final Watchable action) throws FileNotExistsException {
		if (!watch.isDirectory()) {
			throw new FileNotExistsException("File "+watch+" is not a directory!");
		}
		
		if (timer!=null && timer.isRunning()) {
			return false;
		}
		
		//WatchService service=new watch
		
		return true;
	}
	
	public synchronized void stop(){
		if (timer!=null && timer.isRunning()) {
			timer.stop();	
		}
	}
	
	private class HashSetFile extends HashSet<KFile> {
		
		public HashSetFile addAllFiles(final Collection<? extends KFile> c) {
			super.addAll(c);
			return this;
		}
	}
	
}
