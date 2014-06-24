package com.skettidev.pipdroid;

public class Stat {
	
	private int val;
	
	public Stat(){
		val = 0;
	}
	
	public Stat(int statValue){
		val = statValue;
	}
	
	public void setValue(int newValue){
		val = newValue;
	}
	
	public int getValue(){
		return val;
	}
}
