package app;

import java.util.HashMap;
import java.util.Vector;

public class DayTime {
	
	public DayTime(){
		Vector<EachHour> timeInMonday = setTimeInADay();
		dayAndTime.put("Mon", timeInMonday);
		
		Vector<EachHour> timeInTuesday = setTimeInADay();
		dayAndTime.put("Tu", timeInTuesday);
		
		Vector<EachHour> timeInWed = setTimeInADay();
		dayAndTime.put("Wed", timeInWed);
		
		Vector<EachHour> timeInTh = setTimeInADay();
		dayAndTime.put("Th", timeInTh);
		
		Vector<EachHour> timeInFri = setTimeInADay();
		dayAndTime.put("Fri", timeInFri);
		
		Vector<EachHour> timeInSat = setTimeInADay();
		dayAndTime.put("Sat", timeInSat);
		
		Vector<EachHour> timeInSun = setTimeInADay();
		dayAndTime.put("Sun", timeInSun);
		
		
		
	}
	
	private HashMap<String, Vector<EachHour>> dayAndTime = new HashMap<String, Vector<EachHour>>();

	public HashMap<String, Vector<EachHour>> getDayAndTime() {
		return dayAndTime;
	}

	private Vector<EachHour> setTimeInADay(){
		Vector<EachHour> timeInADay = new Vector<EachHour> (24);
		for(int i=1; i<=24; i++){
			EachHour eh = new EachHour(i, false);
			timeInADay.add(eh);
		}
		return timeInADay;
	}
	
}
