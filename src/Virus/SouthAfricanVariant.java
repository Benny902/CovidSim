package Virus;

import Population.Person;
import Population.Sick;

public class SouthAfricanVariant implements IVirus {

	private double contagionProbabilityValue;
	private double tryToContagionValue;
	private double tryToKillValue;
	
	
		// Death Probability 
		// age <= 18 : 5% // 18 < age : 8%
				
		// Contagion Probability
		// age <= 18 : 60% // 18 < age : 50%
	
	@Override
	public String toString() {
		return "SouthAfricanVariant";
	}

	@Override
	public double contagionProbability(Person p) {
		if (p.getAge() <= 18) 
			contagionProbabilityValue = 0.6;
		else
			contagionProbabilityValue = 0.5;
		
		return contagionProbabilityValue*p.getContagionProbability();
	}

	@Override
	public boolean tryToContagion(Person p1, Person p2) {
		if (p1.getClass().getName() == "Sick") {
			if (p2.getClass().getName() != "Sick") {
				double d = p1.getDistance(p2);
				double x = p2.contagionProbability()*Math.min(1, 0.14 * Math.pow(Math.E,2-0.25*d));
				double random = Math.random() *  1;
				  if (x >= random)
			        {
					  	p2.contagion(((Sick) p1).getVirus());
			            return true;
			        }
			}
		}
		return false;
	}

	@Override
	public boolean tryToKill(Sick p) {
		double pKill;
		if (p.getAge() <= 18) 
			pKill = 0.05;
		else
			pKill = 0.08;
		
		long t=Simulation.Clock.now()-p.getContagiousTime();
		tryToKillValue = Math.max(0, pKill-0.01*pKill*Math.pow((t-15),2));
		setTryToKillValue(tryToKillValue); // for the <Sick class>
		double num = Math.random(); // random number Between 0 and 1
		if (num < tryToKillValue)
			return true;
		else 
			return false;
	}

	public double getTryToKillValue() {
		return tryToKillValue;
	}

	public void setTryToKillValue(double tryToKillValue) {
		this.tryToKillValue = tryToKillValue;
	}
	
	public double getTryToContagionValue() {
		return tryToContagionValue;
	}

	public void setTryToContagionValue(double tryToContagionValue) {
		this.tryToContagionValue = tryToContagionValue;
	}
}
