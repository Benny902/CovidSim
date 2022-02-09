package Population;

import Country.Settlement;
import Location.Point;
import Virus.IVirus;
public class Sick extends Person{
	
	private long contagiousTime;
	private IVirus virus;
	
	public Sick(int age, Point location,Settlement settlement, IVirus virus,long contagiousTime) {
		super(age, location, settlement);
		this.virus=virus;
	}
	
	@Override
	public
	double contagionProbability() {
		// TODO Auto-generated method stub
		return 0; // he is SICK he cant "get sick"
	}
	
	Person recover(Person p) {
		Convalescent pCopy;
		pCopy = (Convalescent)p;
		return pCopy;
	}
	
	IVirus virus() {
		return virus;
	}
	
	boolean tryToDie() {
		return virus.tryToKill(this);
	}
	
	public long getContagiousTime() {
		return contagiousTime;
	}
	
	@Override
	public String toString() {
		return "Person Status: " + getClass().getSimpleName() 
				+ ",  Variant: " + getVirus().toString()
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

	public IVirus getVirus() {
		return virus;
	}

	public void setVirus(IVirus virus) {
		this.virus = virus;
	}

	
	

}
