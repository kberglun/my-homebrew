package com.kbit.domain.types;

public class KInteger extends AbstractKObject<Integer>{

	@Override
	public AbstractKObject<Integer> setValue(final String value) {
		setValue(Integer.parseInt(value));
		return this;
	}

	
	
}
