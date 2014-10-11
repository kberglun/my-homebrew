package com.kbit.utilities.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogEntry {
	
	private static final DateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final LogType type;
	public final String message;
	public final StackTraceElement[] stackTrace; 
	public final Date timestamp=Calendar.getInstance().getTime();
	
	public LogEntry(final LogType type, final Exception exception){
		this.type=type;
		this.message=exception.getMessage();
		this.stackTrace=exception.getStackTrace();
	}
	
	public LogEntry(final LogType type, final String message){
		this.type=type;
		this.message=message;
		this.stackTrace=Thread.currentThread().getStackTrace();
	}

	
	
	@Override
	public String toString() {
		return type.toString()+" "+DATE_FORMAT.format(timestamp)+" "+message;
	}
}
