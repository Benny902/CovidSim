package Population;

import Country.Settlement;
import Location.Point;
import Virus.IVirus;

public class Healthy extends Person{

	public Healthy(int age, Point location,Settlement settlement) {
		super(age, location, settlement);
	}
	
	@Override
	public double contagionProbability() {
		// TODO Auto-generated method stub
		return 1;
	}

	Person vaccinate(Person p) {
		Vaccinated pCopy;
		pCopy = (Vaccinated)p;
		return pCopy;
	}


	@Override
	public String toString() {
		return "Person Status: " + getClass().getSimpleName() 
				+ ",  age: " + getAge() 
				+ ",  Location: X=" + getLocation().getX() + ", Y=" + getLocation().getY()
				+ ",  settlement: " + getSettlement().getName();
	}

	@Override
	public
	Person contagion(IVirus virus) {
		// TODO Auto-generated method stub
		return super.contagion(virus);
	}

	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return super.getAge();
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return super.getLocation();
	}

	@Override
	public Settlement getSettlement() {
		// TODO Auto-generated method stub
		return super.getSettlement();
	}

	@Override
	public double getDistance(Person p2) {
		// TODO Auto-generated method stub
		return super.getDistance(p2);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
