package com.kbit.utilities.testMain;


public class Main {

	public static void main(String[] args) {
		
		Run run=new Run();
		
		Thread thread=new Thread(run);
		
		thread.start();
		
		

	}
	
	
	

}
