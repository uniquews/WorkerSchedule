package app;

import java.util.ArrayList;

public class Person {
	private String name;
	private ArrayList <String> availablePeriod = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getAvailablePeriod() {
		return availablePeriod;
	}
	public void setAvailablePeriod(String availablePeriod) {
		this.availablePeriod.add(availablePeriod);
	}
	
}
