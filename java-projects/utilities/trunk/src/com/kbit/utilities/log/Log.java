package com.kbit.utilities.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kbit.domain.types.KFile;

public class Log {

	private static final List<LogEntry> entries=new ArrayList<LogEntry>();
	
	
	public synchronized static void addEntry(final LogType type, final Exception exception){
		entries.add(new LogEntry(type, exception));
	}
	
	public synchronized static void addEntry(final LogType type, final String message){
		entries.add(new LogEntry(type, message));
	}
	
	public synchronized static void debug(final String message){
		entries.add(new LogEntry(LogType.Debug, message));
	}
	
	/*
	public synchronized static void debug(final Exception exeption){
		entries.add(new LogEntry(LogType.Debug, exeption));
	}
	*/

	public synchronized static void info(final String message){
		entries.add(new LogEntry(LogType.Info, message));
	}
	
	/*
	public synchronized static void info(final Exception exeption){
		entries.add(new LogEntry(LogType.Info, exeption));
	}
	*/
	
	public synchronized static void warning(final String message){
		entries.add(new LogEntry(LogType.Warning, message));
	}
	
	/*
	public synchronized static void warning(final Exception exeption){
		entries.add(new LogEntry(LogType.Warning, exeption));
	}
	*/
	
	public synchronized static void exeption(final String message){
		entries.add(new LogEntry(LogType.Error, message));
	}
	
	public synchronized static void exeption(final Exception exeption){
		entries.add(new LogEntry(LogType.Error, exeption));
	}
	
	public synchronized static void print(){
		for(final LogEntry entry: entries){
			if (entry.type==LogType.Error || entry.type==LogType.Warning){
				System.err.println(entry.toString());
			} else {
				System.out.println(entry.toString());
			}
		}
	}
	
	public synchronized static void writeToFile(KFile file){
		for(final LogEntry entry: entries){
			if (entry.type==LogType.Error || entry.type==LogType.Warning){
				System.err.println(entry.toString());
			} else {
				System.out.println(entry.toString());
			}
		}
	}
}
