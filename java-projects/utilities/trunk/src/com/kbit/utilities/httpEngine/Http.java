package com.kbit.utilities.httpEngine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.kbit.domain.exception.file.FileAlreadyExistsException;
import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.exception.file.FileSecurityException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.log.Log;
import com.kbit.utilities.types.KUrl;
import com.kbit.utilities.types.KWebSource;

public class Http {

	private static final int BYTE_LENGTH=1024;
	
	public static KWebSource get(final KUrl url) throws HttpException{
		URL netUrl;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			netUrl = new URL(url.getValue());
			conn = (HttpURLConnection) netUrl.openConnection();
			conn.setRequestMethod("GET");
			Log.debug("Connecting to "+url);
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			Log.debug("Reading from "+url);
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
			Log.debug("Done reading from "+url);
		} catch (Exception e) {
			throw new HttpException(e);
		}
		return (KWebSource) new KWebSource().setValue(result);
	}
	
	public static void download(final KFile file, final KUrl url) throws HttpException, FileException {
		if (file.exists()) {
			throw new FileAlreadyExistsException(file);
		}
	    try {
	    	Log.debug("Connecting to "+url);
	    	BufferedInputStream in = new BufferedInputStream(new URL(url.getValue()).openStream());
	    	FileOutputStream fout = new FileOutputStream(file.getPath());
		    
	        final byte data[] = new byte[BYTE_LENGTH];
	        int count;
	        Log.debug("Reading from "+url+" into file "+file);
	        while ((count = in.read(data, 0, BYTE_LENGTH)) != -1) {
	            fout.write(data, 0, count);
	        }
	        in.close();
	        fout.close();
	        Log.debug("Done reading from "+url+" into file "+file);
	    } catch(SecurityException e) {
	    	throw new FileSecurityException(e);
	    } catch(IOException e) {
	    	throw new HttpException(e);
	    } 
	}

}
