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
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			throw new HttpException(e);
		}
		return (KWebSource) new KWebSource().setValue(result);
	}
	
	public static void download(final KFile file, final String urlString) throws HttpException, FileException {
		if (file.exists()) {
			throw new FileAlreadyExistsException(file);
		}
	    try {
	    	BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());
		    FileOutputStream fout = new FileOutputStream(file.getValue().getAbsolutePath());

	        final byte data[] = new byte[BYTE_LENGTH];
	        int count;
	        while ((count = in.read(data, 0, BYTE_LENGTH)) != -1) {
	            fout.write(data, 0, count);
	        }
	        in.close();
	        fout.close();
	    } catch(SecurityException e) {
	    	throw new FileSecurityException(e);
	    } catch(IOException e) {
	    	throw new HttpException(e);
	    } 
	}

}
