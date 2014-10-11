package com.kbit.utilities.archive;


public class ArchiveEntry {

	public final String timestap;
	public final int sizeMB;
	public final String fileName;
	public final boolean isDirectory;
	
	public ArchiveEntry(final String timestap,final int sizeMB,final String fileName,final boolean isDirectory){
		this.timestap=timestap;
		this.sizeMB=sizeMB;
		this.fileName=fileName;
		this.isDirectory=isDirectory;
	}
	
	
	@Override
	public String toString() {
		if (isDirectory) {
			return "Directory: "+fileName;
		} 
		else {
			return "File: " +fileName + " Size (MB):" + sizeMB;	
		}
		
	}
}
