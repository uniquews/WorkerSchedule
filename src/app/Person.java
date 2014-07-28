package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Person {
	private String name;
	
	//A sentence describes the available time
	private ArrayList <String> availablePeriod = new ArrayList<String>();
	
	//The total available time sum
	private int rawSlot = 0;
	
	//The total working hours now
	private int satisfiedHours = 0;
	
	// In order to fill at least 8 hours, how many hours do he/she need
	private int needHours = 8;
	
	public int getNeedHours() {
		return needHours;
	}
	
	public void updateNeedHours(){
		this.needHours = 8 - satisfiedHours;
		return;
	}
	
	
	public int getSatisfiedHours() {
		return satisfiedHours;
	}
	public void updateSatisfiedHours(int satisfiedHours) {
		this.satisfiedHours = satisfiedHours;
	}
	//Monday -- Range
	private HashMap<String, Vector<Range> > allAvailableSlots = new HashMap<String, Vector<Range> >();
	

	public int getRawSlot() {
		return rawSlot;
	}
	public void setRawSlot(int rawSlot) {
		this.rawSlot += rawSlot;
	}
	
	public void updateRawSlot (int rawSlot){
		this.rawSlot = rawSlot;
	}

	public HashMap<String, Vector<Range>> getAllAvailableSlots() {
		return allAvailableSlots;
	}
	public void setAllAvailableSlots(String daySign, Range r) {
		if(this.allAvailableSlots.containsKey(daySign) == false){
			Vector<Range> ranges = new Vector<Range>();
			ranges.add(r);
			this.allAvailableSlots.put(daySign, ranges);
		}else{
			Vector<Range> ranges = this.allAvailableSlots.get(daySign);
			ranges.add(r);
		}
		
	}
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
