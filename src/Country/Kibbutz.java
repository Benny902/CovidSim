package Country;

import Location.Location;

public class Kibbutz extends Settlement {

	public Kibbutz(String name,Location location,int numOfPeople) {
		super(name, location, numOfPeople);
	}

	//C = 0.45 + (Math.pow((Math.pow(1.5, C)*P-0.4),3))
	
}
