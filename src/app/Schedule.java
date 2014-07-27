package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Schedule {

	private HashMap<String, HashMap<Range, Vector<Person>>> dayRangeToPerson = new HashMap<String, HashMap<Range, Vector<Person>>>();
	private HashMap<String, HashMap<Range, Vector<Person>>> conflictDayRangeToPerson = new HashMap<String, HashMap<Range, Vector<Person>>>();

	public Schedule(ConvertDayToPerson con) {
		this.dayRangeToPerson = con.getDayRangeToPerson();
	}

	public void scheduleWork() {
		DayTime dt = new DayTime();
		HashMap<String, Vector<EachHour>> dayTime = dt.getDayAndTime();

		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPeroson : this.dayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPeroson.getKey();
			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPeroson.getValue();
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
						person.updateSatisfiedHours((int)(end - start));
						person.updateNeedHours();

					}
					// }

				} else {
					conflictDayRangeToPerson.put(dayName, rangesToPerson);
				}
			}
		}

	}
}
