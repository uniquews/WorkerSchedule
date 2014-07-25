package test;

import app.ReadJsonFile;

public class ReadJsonFileTest {
	public static void main(String[] args) {
		ReadJsonFile test = new ReadJsonFile();
		test.readJson("shopperAvailability.json");
	}
}
