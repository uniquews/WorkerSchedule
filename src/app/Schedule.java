package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class Schedule {

	private HashMap<String, HashMap<Range, Vector<Person>>> dayRangeToPerson = new HashMap<String, HashMap<Range, Vector<Person>>>();
	private HashMap<String, ArrayList<HashMap<Range, Vector<Person>>>> diffDayWithRangeAndConflictPerson = new HashMap<String, ArrayList<HashMap<Range, Vector<Person>>>>();
	private ArrayList<Person> unsatidfiedPersons = new ArrayList<Person>();
	private DayTime dt = new DayTime();
	private HashMap<String, Vector<EachHour>> dayTime = dt.getDayAndTime();

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

		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPerson : this.dayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPerson.getKey();
			diffDayWithRangeAndConflictPerson.put(dayName, new ArrayList<HashMap<Range, Vector<Person>>>());

			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPerson.getValue();
			for (Map.Entry<Range, Vector<Person>> each : rangesToPerson.entrySet()) {
				Range r = each.getKey();
				Vector<Person> allPersonsInSameRange = each.getValue();
				int start = r.getStart();
				int end = r.getEnd();

				if (end - start == 2) {
					if (allPersonsInSameRange.size() == 1) { // only this person
																// can
						// work at this time
						Person person = allPersonsInSameRange.get(0);
						if (person.getNeedHours() <= 0) {
							continue;
						}
						boolean canChoose = true;
						Vector<EachHour> timeList = this.dayTime.get(dayName);
						for (int i = start; i < end; i++) {
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
							for (int i = start; i < end; i++) {
								for (EachHour eachHour : timeList) {
									if (eachHour.getHour() == i) {
										eachHour.setChoosen(true);
									}
								}
							}

							person.updateRawSlot(person.getRawSlot() - (end - start));
							person.updateSatisfiedHours(person.getSatisfiedHours() + (end - start));
							person.updateNeedHours();

							System.out.println(person.getName() + " will work on " + dayName + " from " + start + " to " + end);

						}

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
		}

		// displayConflictPersonInARange();
		// displayEachDayHour();

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

	public void displayEachDayHour() {
		for (Map.Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay : this.diffDayWithRangeAndConflictPerson.entrySet()) {
			String dayName = eachDay.getKey();
			System.out.println(dayName);
			Vector<EachHour> todayEachHour = dayTime.get(dayName);
			System.out.println("++++++++++++++++++Check today each hour+++++++++++++++++++++");
			for (EachHour eh : todayEachHour) {
				System.out.print(eh.getHour() + " " + eh.getChoose() + ";       ");
			}
			System.out.println();
		}
	}

	public void displayConflictPersonInARange() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++SHOW CONFLICT+++++++++++++++++++++++++++++++++++++++");
		for (Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay : this.diffDayWithRangeAndConflictPerson.entrySet()) {
			String dayName = eachDay.getKey();
			System.out.println("The day is: " + dayName);
			ArrayList<HashMap<Range, Vector<Person>>> allRanges = eachDay.getValue();
			for (HashMap<Range, Vector<Person>> eachRange : allRanges) {
				for (Map.Entry<Range, Vector<Person>> r : eachRange.entrySet()) {
					Range rr = r.getKey();
					int start = rr.getStart();
					int end = rr.getEnd();

					System.out.println("The range is from " + start + " to " + end);
					System.out.println("The conflict people: ");
					Vector<Person> personList = r.getValue();
					for (Person p : personList) {
						System.out.print(p.getName() + ", ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++End Show+++++++++++++++++++++++++++++++++++++++");
		return;
	}

	public void setUnsatisfiedPerson(ArrayList<Person> shopperList) {
		for (Person p : shopperList) {
			if (p.getNeedHours() > 0) {
				if (p.getRawSlot() < p.getNeedHours()) {
					// before the algorithm, we can check if there is a solution
					System.out.println("Cannot generate successful plan for workers. Please enlarge " + p.getName() + " available working time");
					return;
				} else {
					// System.out.println(p.getName());
					this.unsatidfiedPersons.add(p);
				}
			}
		}

		return;
	}

	public void beforeScheduleCheckRange() {
		for (Map.Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay : this.diffDayWithRangeAndConflictPerson.entrySet()) {
			String dayName = eachDay.getKey();

			Vector<EachHour> timeList = dayTime.get(dayName);

			ArrayList<HashMap<Range, Vector<Person>>> allRangesInSameDay = eachDay.getValue();

			for (HashMap<Range, Vector<Person>> eachRange : allRangesInSameDay) {
				for (Map.Entry<Range, Vector<Person>> rangePerson : eachRange.entrySet()) {
					Range r = rangePerson.getKey();
					// if r is false, then we need to check it
					if (r.isChoosen() == false) {
						int start = r.getStart();
						int end = r.getEnd();
						for (int timeIndex = start; timeIndex < end; timeIndex++) {
							for (EachHour eachHour : timeList) {
								if (eachHour.getHour() == timeIndex) {
									if (eachHour.isChoosen() == true) {
										r.setChoosen(true);

										for (Person p : rangePerson.getValue()) {
											p.updateRawSlot(p.getRawSlot() - (end - start));
										}

									}
								}
							}
						}
					}

				}
			}

		}

	}

	public void scheduleConflict() {
		for (Map.Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay : this.diffDayWithRangeAndConflictPerson.entrySet()) {
			String dayName = eachDay.getKey();
			// Vector<EachHour> todayEachHour = dayTime.get(dayName);
			// +++++++++++++++++++++++++++++++++++++output++++++++++++++++++++++++++++++++++++
			// System.out.println("++++++++++++++++++Check today each hour+++++++++++++++++++++");
			// for (EachHour eh : todayEachHour) {
			// System.out.print(eh.getHour() + " " + eh.getChoose() +
			// ";       ");
			// }
			// System.out.println();

			ArrayList<HashMap<Range, Vector<Person>>> allRangesInSameDay = eachDay.getValue();

			dfsMapEntry(eachDay, allRangesInSameDay, 0);

		}
	}

	public void dfsMapEntry(Map.Entry<String, ArrayList<HashMap<Range, Vector<Person>>>> eachDay, ArrayList<HashMap<Range, Vector<Person>>> allRangesInSameDay, int startIndex) {
		String dayName = eachDay.getKey();

		if (startIndex > allRangesInSameDay.size() - 1) {
			return;
		} else {
			for (int i = startIndex; i < allRangesInSameDay.size(); i++) {
				HashMap<Range, Vector<Person>> eachRange = allRangesInSameDay.get(i);
				for (Map.Entry<Range, Vector<Person>> rangePerson : eachRange.entrySet()) {
					Range r = rangePerson.getKey();
					int start = r.getStart();
					int end = r.getEnd();

					// check if this range is available
					boolean canChoose = true;
					Vector<EachHour> timeList = this.dayTime.get(dayName);
					for (int timeIndex = start; timeIndex < end; timeIndex++) {
						for (EachHour eachHour : timeList) {
							if (eachHour.getHour() == timeIndex) {
								if (eachHour.isChoosen() == true) {
									canChoose = false;
									break;
								}
							}
						}
					}

					if (canChoose == false)
						continue;
					else {
						Vector<Person> allPersonsInRange = rangePerson.getValue();

						// remove the satisfied person
						// Iterator<Person> iter =
						// this.unsatidfiedPersons.iterator();
						// while (iter.hasNext()) {
						// Person tempPerson = iter.next();
						// if (tempPerson.getSatisfiedHours() >= 8) {
						// iter.remove();
						// }
						// }

						for (Person unSatisfiedPerson : this.unsatidfiedPersons) {
							if (allPersonsInRange.contains(unSatisfiedPerson) && unSatisfiedPerson.getNeedHours() > 0) {
								// If one person work

								boolean canChooseInBackTrack = true;
								for (int timeIndexForBackTrack = start; timeIndexForBackTrack < end; timeIndexForBackTrack++) {
									for (EachHour eachHour : timeList) {
										if (eachHour.getHour() == timeIndexForBackTrack) {
											if (eachHour.isChoosen() == true) {
												canChooseInBackTrack = false;
												break;
											}
										}
									}
								}

								if (canChooseInBackTrack) {
									unSatisfiedPerson.updateRawSlot(unSatisfiedPerson.getRawSlot() - (end - start));
									unSatisfiedPerson.updateSatisfiedHours(unSatisfiedPerson.getSatisfiedHours() + (end - start));
									unSatisfiedPerson.updateNeedHours();
									for (Person otherUnSatisfiedPerson : this.unsatidfiedPersons) {
										// others cannot choose to work
										if (otherUnSatisfiedPerson.getName() != unSatisfiedPerson.getName() && allPersonsInRange.contains(otherUnSatisfiedPerson)
												&& otherUnSatisfiedPerson.getNeedHours() > 0) {
											int remainAvailable = otherUnSatisfiedPerson.getRawSlot();
											remainAvailable -= (end - start);
											otherUnSatisfiedPerson.updateRawSlot(remainAvailable);
										}
									}

									// check all unsatisfied person could
									// continue
									if (checkChoosingCorrent() == false) {
										// assign fail, we need to roll back
										unSatisfiedPerson.updateRawSlot(unSatisfiedPerson.getRawSlot() + (end - start));
										unSatisfiedPerson.updateSatisfiedHours(unSatisfiedPerson.getSatisfiedHours() - (end - start));
										unSatisfiedPerson.updateNeedHours();

										for (Person otherUnSatisfiedPerson : this.unsatidfiedPersons) {
											// others cannot choose to work
											if (otherUnSatisfiedPerson.getName() != unSatisfiedPerson.getName() && allPersonsInRange.contains(otherUnSatisfiedPerson)
													&& otherUnSatisfiedPerson.getNeedHours() > 0) {
												int remainAvailable = otherUnSatisfiedPerson.getRawSlot();
												remainAvailable += (end - start);
												otherUnSatisfiedPerson.updateRawSlot(remainAvailable);
											}
										}

										// try next person in unsatisfied list
										continue;
									} else {
										// assign success

										r.setChoosen(true);
										for (int timeIndex = start; timeIndex < end; timeIndex++) {
											for (EachHour eachHour : timeList) {
												if (eachHour.getHour() == timeIndex) {
													eachHour.setChoosen(true);
												}
											}
										}
										System.out.println(unSatisfiedPerson.getName() + " will work on " + dayName + " from " + start + " to " + end);
										dfsMapEntry(eachDay, allRangesInSameDay, i + 1);

										// unSatisfiedPerson.updateRawSlot(unSatisfiedPerson.getRawSlot()
										// + (end - start));
										// unSatisfiedPerson.updateSatisfiedHours(unSatisfiedPerson.getSatisfiedHours()
										// - (end - start));
										// unSatisfiedPerson.updateNeedHours();
										//
										// for (Person otherUnSatisfiedPerson :
										// this.unsatidfiedPersons) {
										// // others cannot choose to work
										// if (otherUnSatisfiedPerson.getName()
										// !=
										// unSatisfiedPerson.getName() &&
										// allPersonsInRange.contains(otherUnSatisfiedPerson))
										// {
										// int remainAvailable =
										// otherUnSatisfiedPerson.getRawSlot();
										// remainAvailable += (end - start);
										// otherUnSatisfiedPerson.updateRawSlot(remainAvailable);
										// }
										// }
										//
										// r.setChoosen(false);
										// for (int timeIndex = start; timeIndex
										// <
										// end; timeIndex++) {
										// for (EachHour eachHour : timeList) {
										// if (eachHour.getHour() == timeIndex)
										// {
										// eachHour.setChoosen(false);
										// }
										// }
										// }

									}

								}

							}
						}

					}

				}

			}

		}

		return;

	}

	public void afterSchduleConflictSetRange() {
		for (Map.Entry<String, HashMap<Range, Vector<Person>>> eachDayRangeToPerson : this.dayRangeToPerson.entrySet()) {
			String dayName = eachDayRangeToPerson.getKey();
			HashMap<Range, Vector<Person>> rangesToPerson = eachDayRangeToPerson.getValue();
			for (Map.Entry<Range, Vector<Person>> each : rangesToPerson.entrySet()) {
				Range allRange = each.getKey();
				// we need to check if this range is also not chosen in conflict
				// range
				if (allRange.isChoosen() == false) {
					ArrayList<HashMap<Range, Vector<Person>>> todayRanges = this.diffDayWithRangeAndConflictPerson.get(dayName);
					if (todayRanges.size() == 0)
						continue;
					for (int i = 0; i < todayRanges.size(); i++) {
						HashMap<Range, Vector<Person>> eachRange = todayRanges.get(i);
						for (Map.Entry<Range, Vector<Person>> rangePersons : eachRange.entrySet()) {

							Range conflictRange = rangePersons.getKey();
							// if we set it true in conflict but it still
							// remains false
							if (conflictRange.getStart() == allRange.getStart() && conflictRange.getEnd() == allRange.getEnd() && conflictRange.isChoosen() == true && allRange.isChoosen() == false) {
								allRange.setChoosen(true);
							}
						}
					}
				}
			}
		}

		return;
	}

	public boolean checkChoosingCorrent() {
		for (Person unSatisfiedPerson : this.unsatidfiedPersons) {
			int remainHours = unSatisfiedPerson.getRawSlot();
			int needHours = unSatisfiedPerson.getNeedHours();

			if (remainHours < needHours) {
				return false;
			}
		}

		return true;
	}

}
