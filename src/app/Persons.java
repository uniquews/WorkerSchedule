package app;

import java.util.ArrayList;

public class Persons {
	private ArrayList<Person> Shoppers = new ArrayList<Person>();

	public ArrayList<Person> getShoppers() {
		return Shoppers;
	}

	public void setShoppers(Person shopper) {
		this.Shoppers.add(shopper);
	}
}
