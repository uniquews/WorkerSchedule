package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class Schedule {

	private HashMap<String, HashMap<Range, Vector<Person>>> dayRangeToPerson = new HashMap<String, HashMap<Range, Vector<Person>>>();
	private HashMap<String, ArrayList<HashMap<Range, Vector<Person>>>> diffDayWithRangeAndConflictPerson = new HashMap<String, ArrayList<HashMap<Range, Vector<Person>>>>();
	private ArrayList<Person> unsatidfiedPersons = new ArrayList<Person>();

	public HashMap<String, HashMap<Range, Vector<Person>>> getDayRangeToPerson() {
		return dayRangeToPerson;
	}

	public HashMap<String, ArrayList<HashMap<Range, Vector<Person>>>> getDiffDayWithRangeAndConflictPerson() {
		return diffDayWithRangeAndConflictPerson;
	}

	public ArrayList<Person> getUnsatidfiedPersons() {
		return unsatidfiedPersons;
	}

	public Schedule(ConvertDayToPerson con) {
		this.dayRangeToPerson = con.getDayRangeToPerson();
	}

	public void scheduleNonConflictRanges() {
		DayTime dt = new DayTime();
		HashMap<String, Vector<EachHour>> dayTime = dt.getDayAndTime();

		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPerson : this.dayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPerson.getKey();
			diffDayWithRangeAndConflictPerson.put(dayName, new ArrayList<HashMap<Range, Vector<Person>>>());

			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPerson.getValue();
			for (Map.Entry<Range, Vector<Person>> each : rangesToPerson.entrySet()) {
				Range r = each.getKey();
				Vector<Person> allPersonsInSameRange = each.getValue();
				Integer start = new Integer(r.getStart());
				Integer end = new Integer(r.getEnd());
				if (allPersonsInSameRange.size() == 1) { // only this person can
															// work at this time
					// if (end - start == 2) {
					boolean canChoose = true;
					Vector<EachHour> timeList = dayTime.get(dayName);
					for (Integer i = start; i < end; i++) {
						for (EachHour eachHour : timeList) {
							if (eachHour.getHour() == i) {
								if (eachHour.isChoosen() == true) {
									canChoose = false;
								}
							}
						}
					}

					if (canChoose == false)
						continue;
					else {
						r.setChoosen(true);
						for (Integer i = start; i < end; i++) {
							for (EachHour eachHour : timeList) {
								if (eachHour.getHour() == i) {
									eachHour.setChoosen(true);
								}
							}
						}

						Person person = allPersonsInSameRange.get(0);
						person.updateRawSlot((int) Integer.valueOf(person.getRawSlot()) - (end - start));
						person.updateSatisfiedHours((int) (end - start));
						person.updateNeedHours();

					}
					// }

				} else {
					ArrayList<HashMap<Range, Vector<Person>>> inSameDay = this.diffDayWithRangeAndConflictPerson.get(dayName);
					Vector<Person> allConflictPersons = new Vector<Person>();
					for (int l = 0; l < allPersonsInSameRange.size(); l++)
						allConflictPersons.add(allPersonsInSameRange.get(l));
					HashMap<Range, Vector<Person>> conflictPersonList = new HashMap<Range, Vector<Person>>();
					Range newRange = new Range(r.getStart(), r.getEnd());
					conflictPersonList.put(newRange, allConflictPersons);
					inSameDay.add(conflictPersonList);

				}
			}
		}
		
		displayConflictPersonInARange();
		return;

	}

	public void displayDayRangeToPerson(HashMap<String, HashMap<Range, Vector<Person>>> dayRangeToPerson) {
		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPeroson : dayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPeroson.getKey();
			System.out.println("The day is " + dayName);
			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPeroson.getValue();
			for (Map.Entry<Range, Vector<Person>> each : rangesToPerson.entrySet()) {
				Range r = each.getKey();
				System.out.println("The range is from " + r.getStart() + " to " + r.getEnd());
				Vector<Person> allPersonsInSameRange = each.getValue();
				String allPerson = "In this range, there is/are: ";
				for (Person p : allPersonsInSameRange) {
					allPerson += p.getName() + ", ";
				}
				System.out.println(allPerson);
			}
			System.out.println();
		}
		
		return;
	}

	public void displayConflictPersonInARange() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++SHOW CONFLICT+++++++++++++++++++++++++++++++++++++++");
		for (Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay : this.diffDayWithRangeAndConflictPerson.entrySet()) {
			String dayName = eachDay.getKey();
			System.out.println("The day is: "+ dayName);
			ArrayList<HashMap<Range, Vector<Person>>> allRanges = eachDay.getValue();
			for(HashMap<Range, Vector<Person>> eachRange : allRanges){
				for(Map.Entry<Range, Vector<Person>> r:eachRange.entrySet()){
					Range rr = r.getKey();
					int start = rr.getStart();
					int end = rr.getEnd();
					
					System.out.println("The range is from "+ start+" to "+end);
					System.out.println("The conflict people: ");
					Vector<Person> personList = r.getValue();
					for(Person p : personList){
						System.out.print(p.getName()+", ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++End Show+++++++++++++++++++++++++++++++++++++++");
		return;
	}
	
	
	
	public void setUnsatisfiedPerson(ArrayList<Person> shopperList){
		
		for(Person p: shopperList){
			if(p.getNeedHours() >0){
				if(p.getRawSlot() < p.getNeedHours()){
					// before the algorithm, we can check if there is a solution
					System.out.println("Cannot generate successful plan for workers. Please enlarge "+ p.getName()+" available working time");
					return;
				}else{
					System.out.println(p.getName());
					this.unsatidfiedPersons.add(p);
				}
			}
		}
		
		return;
	}
}
