package com.kbit.domain.types;

public abstract class AbstractKObject<T extends Comparable<T>> {

	private T value;

	public boolean isEmpty(){
		return value==null;
	}
	
	final public T getValue(){
		return value;
	}
	
	final public AbstractKObject<T> setValue(final T value){
		this.value=value;
		return this;
	}
	
	public abstract AbstractKObject<T> setValue(final String value);
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " value:'" + value.toString() + "'"; 
	}
}
