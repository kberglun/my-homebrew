package com.kbit.utilities.testMain;

import java.util.List;

import com.kbit.domain.types.KFile;
import com.kbit.utilities.archive.Archive;
import com.kbit.utilities.archive.ArchiveEntry;
import com.kbit.utilities.archive.ArchiveException;
import com.kbit.utilities.log.Log;

public class Main {

	public static void main(String[] args) {
		KFile file=new KFile();
		file.setValue("C:\\Temp\\Test\\Test.zip");
		
		try {
			List<ArchiveEntry> entries=Archive.list(file);
			for (ArchiveEntry entry:entries){
				System.out.println(entry.toString());	
			}
			
		} catch (ArchiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.print();
		

	}

}
