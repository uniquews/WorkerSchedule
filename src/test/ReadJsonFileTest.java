package test;

import java.util.ArrayList;

import app.Person;
import app.Persons;
import app.ReadJsonFile;

public class ReadJsonFileTest {
	public static void main(String[] args) {
		
		ReadJsonFile test = new ReadJsonFile();
		Persons shoppers = new Persons();
		test.readJson("shopperAvailability.json", shoppers);
		
		
		ArrayList<Person> shopperList = shoppers.getShoppers();
		
		for(Person shopper : shopperList){
			System.out.print(shopper.getName()+ " has");
			ArrayList<String> availableTime = shopper.getAvailablePeriod();
			String time = "";
			for(String eachPeriod : availableTime){
				time+=eachPeriod+"; ";
			}
			
			System.out.println(" "+ time);
		}
		
	}
}
