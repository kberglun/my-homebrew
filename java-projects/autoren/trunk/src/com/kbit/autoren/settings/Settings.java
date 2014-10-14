package com.kbit.autoren.settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kbit.autoren.settings.Setting.SettingBuilder;
import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.exception.file.FileNotExistsException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.log.Log;

public final class Settings implements Iterable<Setting> {

	private static final String SETTING_FILE_EXTENSION=".ksettings";
	
	private final KFile settingsFile=new KFile();
	private List<Setting> settings;
	
	public Settings(final KFile settingsFile) throws FileException{
		if (settingsFile.getExtension()!=SETTING_FILE_EXTENSION) {
			throw new FileNotExistsException("Illegal extension "+settingsFile.getExtension()+ " allowed only "+SETTING_FILE_EXTENSION);
		}
			
		this.settingsFile.setValue(settingsFile.getValue());
		load();
	}
	
	@Override
	public Iterator<Setting> iterator() {
		return settings.iterator();
	}
	
	public void load() throws FileException{
		Log.debug("Loading settings file "+settingsFile.getPath());
		settings=new ArrayList<Setting>();
		
		
		for(final String line:settingsFile.readAllLines()){
			for(final String item:line.split("|")) {
				final Setting setting=new SettingBuilder()
				     .setId(0)
				     .setActive(...)
				     .build();
			}
		}
		
	}
	
	public void save(){
		
	}

}
