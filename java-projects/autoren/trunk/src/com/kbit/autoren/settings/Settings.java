package com.kbit.autoren.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kbit.autoren.settings.Setting.SettingBuilder;
import com.kbit.domain.exception.file.FileException;
import com.kbit.domain.exception.file.FileNotExistsException;
import com.kbit.domain.types.KFile;
import com.kbit.utilities.log.Log;
import com.kbit.utilities.types.KUrl;

public final class Settings implements Iterable<Setting> {

	private static final String SETTING_FILE_EXTENSION=".ksettings";
	private static final String SETTING_FILE_SEPARATOR="|";
	private static final String SETTING_FILE_PREFIX="prefix=";
	private static final String SETTING_FILE_SERIES="series=";
	private static final String SETTING_FILE_EPGUIDES="epguides=";
	private static final String SETTING_FILE_PODNAPISI="podnapisi=";
	private static final String SETTING_FILE_ACTIVE="active=";
	
	private final KFile settingsFile=new KFile();
	private final List<Setting> settings=new ArrayList<Setting>();
	private final Map<String, Integer> prefixesAll=new HashMap<String, Integer>();
	
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
		
		int id=0;
		for(final String line:settingsFile.readAllLines()){
			id++;
			boolean isActive=false;
			KUrl epguidesURL=new KUrl();
			KUrl podnapisiURL=new KUrl();
			List<String> prefixes=new ArrayList<String>();
			String series=null;
			
			for(final String token:line.split(SETTING_FILE_SEPARATOR)) {
				final String item=token.trim();
				if (item.startsWith(SETTING_FILE_ACTIVE)) {
					isActive=Boolean.parseBoolean(item.substring(SETTING_FILE_ACTIVE.length()));
				} else 
					
				if (item.startsWith(SETTING_FILE_EPGUIDES)) {
					epguidesURL.setValue(item.substring(SETTING_FILE_EPGUIDES.length()));
				} else
					
				if (item.startsWith(SETTING_FILE_PODNAPISI)) {
					podnapisiURL.setValue(item.substring(SETTING_FILE_PODNAPISI.length()));
				} else
					
				if (item.startsWith(SETTING_FILE_PREFIX)) {
					final String prefix=item.substring(SETTING_FILE_PREFIX.length());
					prefixes.add(prefix);
					prefixesAll.put(prefix, id);
				} else
					
				if (item.startsWith(SETTING_FILE_SERIES)) {
					series=item.substring(SETTING_FILE_SERIES.length());
				} else  
				
				if (item!="") {
					Log.warning("Unidentified settings prefix:\""+item+"\"");
				}
				
			}
			
			final Setting setting=new SettingBuilder()
		     .setId(id)
		     .setActive(isActive)
		     .setEpguidesUrl(epguidesURL)
		     .setPodnapisiUrl(podnapisiURL)
		     .setFilePrefixes(prefixes)
		     .setSeriesName(series)
		     .build();
			
			settings.add(setting);
		}
	
		
	}
	
	public void save(){
		List<String> allLines=new ArrayList<String>();
		
		for(final Setting setting:settings) {
			final SettingsBuilder line=new StringBuilder();
			
			line.append(SETTING_FILE_ACTIVE)
			.append(setting.isActive)
			.append(SETTING_FILE_SEPARATOR)
			
			.append(SETTING_FILE_EPGUIDES)
			.append(setting.epguidesUrl.serialize())
			.append(SETTING_FILE_SEPARATOR)
			
			.append(SETTING_FILE_PODNAPISI)
			.append(setting.podnapisiUrl.serialize())
			.append(SETTING_FILE_SEPARATOR)
			
			.append(SETTING_FILE_SERIES)
			.append(setting.seriesName)
			.append(SETTING_FILE_SEPARATOR);
			
			for(final String prefix:setting.filePrefixes) {
				line.append(SETTING_FILE_PREFIX)
				.append(prefix)
				.append(SETTING_FILE_SEPARATOR);
			}
			allLines.add(line.toString());
		}
		
		settingsFile.writeAllLines():
	}

}
