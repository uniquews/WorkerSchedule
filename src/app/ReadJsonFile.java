package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ReadJsonFile {

	public void readJson(String path, Persons people) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(path);
			JsonReader jsonReader = Json.createReader(stream);
			JsonObject jsonObj = jsonReader.readObject();
			
//			JsonArray array = jsonObj.getJsonArray("PerosonalShoopers");
//
//			JsonObject eachPerson = (jsonObj.getJsonArray("PerosonalShoopers")).getJsonObject(0);
//			System.out.println(eachPerson.getString("name"));
//			for(int i=0; i<array.size(); i++){
//				JsonObject eachPerson1 = array.getJsonObject(i);
//				String personName = eachPerson1.getString("name");
//				System.out.println(personName);
//			}
			for(int i=0; i<jsonObj.getJsonArray("PersonalShoppers").size(); i++){
				JsonObject eachPerson = jsonObj.getJsonArray("PersonalShoppers").getJsonObject(i);
				
				String name = eachPerson.getString("Name");
				Person shopper = new Person();
//				System.out.println(name); // name
				shopper.setName(name);
				
				JsonArray availability = eachPerson.getJsonArray("Availability");
//				System.out.println(availability);
				
				for(int j = 0; j<availability.size(); j++){
					JsonObject eachDay = availability.getJsonObject(j);
//					System.out.println(eachDay);
					String dayName = eachDay.getString("Day");
//					System.out.println(dayName);
					JsonArray timeArray = eachDay.getJsonArray("Time");
					for(int m = 0; m<timeArray.size(); m++){
						JsonObject eachPeriod = timeArray.getJsonObject(m);
						int start = eachPeriod.getInt("From");
						int end = eachPeriod.getInt("To");
						String dayAndTime = dayName +" From "+ start + " To "+end;
						int rawSlot = end - start;
						if(rawSlot <2){
							System.out.println("The "+name +" "+ dayAndTime +" was not added in list");
							continue;
						}
						ArrayList<Range> allPossibleRange = new ArrayList<Range>();
						
						for(int k=2; k<=rawSlot; k++){
							int tempEnd = start+k;
							Range newRange = new Range(start, tempEnd);
							allPossibleRange.add(newRange);
						}
						
						shopper.setRawSlot(rawSlot);
//						System.out.println(dayAndTime);
						shopper.setAvailablePeriod(dayAndTime);
						
						switch(dayName){
						case "Monday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Mon", r);
							}
							break;
						case "Tuesday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Tu", r);
							}
							break;
						case "Wednesday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Wed", r);
							}
							break;
						case "Thursday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Th", r);
							}
							break;
						case "Friday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Fr", r);
							}
							break;
						case "Saturday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Sat", r);
							}
						case "Sunday": 
							for(Range r:allPossibleRange){
								shopper.setAllAvailableSlots("Sun", r);
							}
							break;
						}
						
						allPossibleRange.clear();
					}
				}
				
				people.setShoppers(shopper);
			}

			
//			System.out.println(jsonObj.getJsonArray("PersonalShoppers"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
