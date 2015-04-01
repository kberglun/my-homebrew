package com.kbit.domain.types;


public abstract class AbstractKObject<T extends Comparable<T>> {

	T value;

	public boolean isEmpty(){
		return value==null;
	}
	
	public T getValue(){
		return value;
	}
	
	public AbstractKObject<T> setValue(final T value){
		this.value=value;
		return this;
	}

	@Override
	public String toString() {
		return value==null?"":value.toString(); 
	}
	
	public abstract String serialize();
	
	public abstract void deserialize(final String string);
	
}
