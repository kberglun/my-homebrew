package com.kbit.utilities.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Cmd {

	public static List<String> execute(final String cmd) throws Exception {
		List<String> list=new ArrayList<String>();
		
		final Process p;
		p = Runtime.getRuntime().exec(cmd);
		p.waitFor();
		final BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
 
		String line;
		while ((line = reader.readLine())!= null) {
			list.add(line);
		}
		
		return list;
	}
	
}
