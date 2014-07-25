package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
				Person shooper = new Person();
//				System.out.println(name); // name
				shooper.setName(name);
				
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
//						System.out.println(dayAndTime);
						shooper.setAvailablePeriod(dayAndTime);
					}
				}
				
				people.setShoppers(shooper);
			}

			
//			System.out.println(jsonObj.getJsonArray("PersonalShoppers"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
