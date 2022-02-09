package Population;

import Country.Settlement;
import Location.Point;
import Simulation.Clock;
import Virus.IVirus;

public class Vaccinated extends Person{

	private long vaccinationTime;
	
	@Override
	public String toString() {
		return "Person Status: " + getClass().getSimpleName() 
				+ ",  age: " + getAge() 
				+ ",  Location: X=" + getLocation().getX() + ", Y=" + getLocation().getY()
				+ ",  settlement: " + getSettlement().getName()
				+ ",  VaccinationTime: " + getVaccinationTime();
	}

	@Override
	public
	double contagionProbability() {
		long t = Clock.getTimeNow() - vaccinationTime;
		// t = days passed from the moment of vaccination to the moment of testing 
		double Pt=0; // P(t)
		if (t < 21) 
			Pt = Math.min(1, 0.56+0.15*(Math.sqrt(21-t)));
		else //t >= 21
			Pt = Math.max(0.05, 1.05/(t-14));
	
		return Pt;
	}

	@Override
	public
	Person contagion(IVirus virus) {
		// TODO Auto-generated method stub
		return super.contagion(virus);
	}

	public long getVaccinationTime() {
		return vaccinationTime;
	}

	public void setVaccinationTime(long vaccinationTime) {
		this.vaccinationTime = vaccinationTime;
	}

	public Vaccinated(int age, Point location,Settlement settlement, long vacctinationTime) {
		super(age, location, settlement);
	}

}
