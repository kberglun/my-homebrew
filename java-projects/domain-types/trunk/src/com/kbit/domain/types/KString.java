package com.kbit.domain.types;

public class KString extends AbstractKObject<String> {

	@Override
	public String serialize() {
		return isEmpty()?"":value;
	}

	@Override
	public void deserialize(String string) {
		value=string==null?"":string;
	}


}
