package com.kbit.autoren.settings;

import java.util.List;

import com.kbit.utilities.types.KUrl;

public class Setting {

	public final int id;
	public final List<String> filePrefixes;
	public final String seriesName;
	public final KUrl epguidesUrl;
	public final KUrl podnapisiUrl;
	public final boolean isActive;

	private Setting(final int id, 
			final List<String> filePrefixes, 
			final String seriesName, 
			final KUrl epguidesUrl, 
			final KUrl podnapisiUrl, 
			final boolean isActive){
		super();
		this.id=id;
		this.filePrefixes=filePrefixes;
		this.seriesName=seriesName;
		this.epguidesUrl=epguidesUrl;
		this.podnapisiUrl=podnapisiUrl;
		this.isActive=isActive;
	}

	public static SettingBuilder builder(){
		return new Setting.SettingBuilder();
	}
	
	public static class SettingBuilder{
	
		private int id;
		private List<String> filePrefixes;
		private String seriesName;
		private KUrl epguidesUrl;
		private KUrl podnapisiUrl;
		private boolean isActive;
		
		public SettingBuilder setId(final int id){
			this.id=id;
			return this;
		}

		public SettingBuilder setFilePrefixes(List<String> filePrefixes){
			this.filePrefixes=filePrefixes;
			return this;
		}
		
		public SettingBuilder setSeriesName(final String seriesName){
			this.seriesName=seriesName;
			return this;
		}
		
		public SettingBuilder setEpguidesUrl(final KUrl epguidesUrl){
			this.epguidesUrl=epguidesUrl;
			return this;
		}
		
		public SettingBuilder setPodnapisiUrl(final KUrl podnapisiUrl){
			this.podnapisiUrl=podnapisiUrl;
			return this;
		}

		public SettingBuilder setActive(final boolean isActive){
			this.isActive=isActive;
			return this;
		}
		
		public Setting build(){
			if (epguidesUrl.isEmpty()) {
				final String url="http://epguides.com/"+seriesName.toLowerCase().replace("the ", "")+"/";
				epguidesUrl.setValue(url);
			}
			return new Setting(id,filePrefixes,seriesName,epguidesUrl,podnapisiUrl,isActive);
		}
	}
}
