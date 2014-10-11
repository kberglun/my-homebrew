package com.kbit.utilities.archive;

import java.util.ArrayList;
import java.util.List;

import com.kbit.domain.types.KFile;
import com.kbit.utilities.cmd.Cmd;
import com.kbit.utilities.log.Log;


public class Archive {

	private static final String ZIP_PATH="resources/7zip/7z.exe ";
	private static final String ZIP_LIST=" l ";
	private static final String ZIP_EXCTRACT=" e -y "; // extract without folders, auto answer yes
	private static final String ZIP_OUTPUT=" -o ";
	private static final String ZIP_SNUFF="\"";

	public static List<ArchiveEntry> list(final KFile file) throws ArchiveException{
		if (!file.isFile()) {
			throw new ArchiveException(file+" is not a file");
		}

		Log.debug("List archive "+file);

		final List<ArchiveEntry> entries=new ArrayList<ArchiveEntry>();

		final StringBuilder builder=new StringBuilder();
		builder.append(ZIP_PATH);
		builder.append(ZIP_LIST);

		builder.append(ZIP_SNUFF);
		builder.append(file.getValue().getPath());
		builder.append(ZIP_SNUFF);

		try {
			Log.debug("Execute command "+builder.toString());
			final List<String> output=Cmd.execute(builder.toString());
			boolean startFound=false;
			boolean endFound=false;

			boolean isMultivolume=false;
			boolean isFirstVolume=false;

			Log.debug("Parsing result...");
			for(final String line:output){

				if (line.toLowerCase().startsWith("Characteristics = Volume FirstVolume".toLowerCase())) {
					isFirstVolume=true;
				}

				if (line.toLowerCase().startsWith("Multivolume = +".toLowerCase())) {
					isMultivolume=true;
				}

				if (startFound &&line.startsWith("-------------------")) {
					endFound=true;
				}

				if (startFound && !endFound) {
					final String date=line.substring(0,10);
					final String time=line.substring(11,11+8);
					final String attr=line.substring(20,20+5);
					final String size=line.substring(26,26+12);
					final String name=line.substring(53);

					final boolean isDirectory=attr.startsWith("D");

					int sizeMB=0;
					try{
						sizeMB=Integer.parseInt(size.trim());
						sizeMB /= 1024;
						sizeMB /= 1024;
					} 
					catch (NumberFormatException e) {
						//
					}

					entries.add(new ArchiveEntry(date+' '+time, sizeMB, name, isDirectory));
				}

				if (!startFound && line.startsWith("-------------------")) {
					startFound=true;
				}
			}

			if (isMultivolume && !isFirstVolume) {
				throw new ArchiveException("This is not the first archive in a multiarchive:"+file.getValue().getPath());
			}

		} catch (Exception e) {
			throw new ArchiveException("Exception executing command:"+e.getMessage());
		}

		Log.debug("Done!");
		return entries;
	}


	public static void extract(final KFile file, final KFile dir, final ArchiveEntry entry) throws ArchiveException{
		if (!file.isFile()) {
			throw new ArchiveException(file+" is not a file");
		}

		if (!dir.isDirectory()) {
			throw new ArchiveException(dir+" is not a directory");
		}

		Log.debug("Extract from archive "+file+" file "+entry.fileName+" into "+dir);

		final StringBuilder builder=new StringBuilder();
		builder	.append(ZIP_PATH)
				.append(ZIP_EXCTRACT)
		
				.append(ZIP_OUTPUT)
				.append(ZIP_SNUFF)
				.append(dir)
				.append(ZIP_SNUFF)
		
				.append(' ')
		
				.append(ZIP_SNUFF)
				.append(file.getValue().getPath())
				.append(ZIP_SNUFF)
		
				.append(' ')
		
				.append(ZIP_SNUFF)
				.append(entry.fileName)
				.append(ZIP_SNUFF);

		boolean isOk=false;

		try {
			Log.debug("Execute command "+builder.toString());	
			List<String> output=Cmd.execute(builder.toString());

			for(final String line:output) {
				if (line.startsWith("Everything is Ok")) {
					isOk=true;
				}
			}

		} catch (Exception e) {
			throw new ArchiveException("Exception executing command:"+builder.toString());
		}

		if (!isOk) {
			throw new ArchiveException("Error extracting file "+file+" using command "+builder.toString());
		}

	}

}
