package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ConvertDayToPerson {

	private HashMap<String, HashMap<Range, Vector<Person>>> DayRangeToPerson = new HashMap<String, HashMap<Range, Vector<Person>>>();

	public void convert(Persons shoppers) {

		ArrayList<Person> shopperList = shoppers.getShoppers();

		DayRangeToPerson.put("Mon", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Tu", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Wed", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Th", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Fri", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Sat", new HashMap<Range, Vector<Person>>());
		DayRangeToPerson.put("Sun", new HashMap<Range, Vector<Person>>());

		for (Person shopper : shopperList) {
			String name = shopper.getName();
			HashMap<String, Vector<Range>> allPossibleSlotsForOnePerson = shopper.getAllAvailableSlots();
			addPersonToRange(shopper, "Mon", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Tu", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Wed", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Th", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Fri", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Sat", allPossibleSlotsForOnePerson);
			addPersonToRange(shopper, "Sun", allPossibleSlotsForOnePerson);
		}
	}

	public void addPersonToRange(Person shopper, String daySign, HashMap<String, Vector<Range>> allPossibleSlotsForOnePerson) {
		if (allPossibleSlotsForOnePerson.containsKey(daySign)) {
			HashMap<Range, Vector<Person>> allRangesInSameDay = this.DayRangeToPerson.get(daySign);
			Vector<Range> ranges = allPossibleSlotsForOnePerson.get(daySign);
			for (Range r : ranges) {
				if (allRangesInSameDay.containsKey(r)) {
					Vector<Person> allPersonAvailableInSameRange = allRangesInSameDay.get(r);
					allPersonAvailableInSameRange.add(shopper);
				} else {
					Vector<Person> personListForNewRange = new Vector<Person>();
					personListForNewRange.add(shopper);
					allRangesInSameDay.put(r, personListForNewRange);
				}
			}
		}

	}

	public void displayDayRangeToPerson() {
		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPeroson : this.DayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPeroson.getKey();
			System.out.println("The day is "+dayName);
			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPeroson.getValue();
			for (Map.Entry<Range, Vector<Person>> each : rangesToPerson.entrySet()) {
				Range r = each.getKey();
				System.out.println("The range is from "+ r.getStart() + " to "+ r.getEnd());
				Vector<Person> allPersonsInSameRange = each.getValue();
				String allPerson = "In this range, there is/are: ";
				for (Person p : allPersonsInSameRange) {
					allPerson += p.getName() + ", ";
				}
				System.out.println(allPerson);
			}
			System.out.println();
		}
	}

}
