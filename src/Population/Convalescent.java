package Population;

import Country.Settlement;
import Location.Point;
import Virus.IVirus;

public class Convalescent extends Person{

	IVirus virus;
	
	public Convalescent(int age, Point location,Settlement settlement, IVirus virus) {
		super(age, location, settlement);
	}
	
	@Override
	public double contagionProbability() {
		// TODO Auto-generated method stub
		return 0.2;
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

	@Override
	public String toString() {
		return "Person Status: " + getClass().getSimpleName() 
				+ ",  age: " + getAge() 
				+ ",  Location: X=" + getLocation().getX() + ", Y=" + getLocation().getY()
				+ ",  settlement: " + getSettlement().getName()
				+ ",  Virus caught: " + getVirus();
	}

	public IVirus getVirus() {
		return virus;
	}

	public void setVirus(IVirus virus) {
		this.virus = virus;
	}

	
}
