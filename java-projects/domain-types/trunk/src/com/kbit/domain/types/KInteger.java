package com.kbit.domain.types;

public class KInteger extends AbstractKObject<Integer>{

	@Override
	public String serialize() {
		return isEmpty()?"":getValue().toString();
	}

	@Override
	public void deserialize(final String string) {
		try{
			value=Integer.parseInt(string);
		} catch(NumberFormatException e) {
			value=0;
		}
	}

	
		
	
}
