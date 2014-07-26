package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import app.ConvertDayToPerson;
import app.Person;
import app.Persons;
import app.Range;
import app.ReadJsonFile;

public class ReadJsonFileTest {
	public static void main(String[] args) {
		
		ReadJsonFile test = new ReadJsonFile();
		Persons shoppers = new Persons();
		test.readJson("shopperAvailability.json", shoppers);
		
		ArrayList<Person> shopperList = shoppers.getShoppers();
		
//		for(Person shopper : shopperList){
//			System.out.print(shopper.getName()+ " has");
//			ArrayList<String> availableTime = shopper.getAvailablePeriod();
//			String time = "";
//			for(String eachPeriod : availableTime){
//				time+=eachPeriod+"; ";
//			}
//			
//			System.out.println(" "+ time);
//			
//			System.out.println("The name is "+ shopper.getName());
//			System.out.println("The total remain hours: " + shopper.getRawSlot());
//			HashMap<String, Vector<Range>> allPossibleSlotsForOnePerson = shopper.getAllAvailableSlots();
//			for(Map.Entry<String, Vector<Range>> eachTimeSlot : allPossibleSlotsForOnePerson.entrySet()){
//				String daySign = eachTimeSlot.getKey();
//				System.out.println("The day: "+ daySign);
//				for(Range r :eachTimeSlot.getValue()){
//					System.out.println("From " + r.getStart() +" To "+ r.getEnd());
//				}
//				System.out.println();
//			}
//			
//		}
		
		ConvertDayToPerson con = new ConvertDayToPerson();
		con.convert(shoppers);
		con.displayDayRangeToPerson();
		
	}
}
