package com.kbit.utilities.log;

import java.util.Calendar;
import java.util.Date;

public class LogEntry {
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
}
