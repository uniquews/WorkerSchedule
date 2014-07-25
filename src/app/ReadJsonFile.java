package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ReadJsonFile {

	public void readJson(String path) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(path);
			JsonReader jsonReader = Json.createReader(stream);
			JsonObject jsonObj = jsonReader.readObject();

			System.out.println(jsonObj.getJsonArray("PersonalShoppers"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
