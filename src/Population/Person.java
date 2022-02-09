package Population;
import Country.Settlement;
import Location.Point;
import Virus.IVirus;

public abstract class Person {
	
	private int age;
	private Point location;
	private Settlement settlement;
	
	public Person(int age, Point location,Settlement settlement) {
		this.age = age;
		this.location = location;
		this.settlement= settlement;
	}
	
	
	public double contagionProbability() {
		return 1;
	}

	public Person contagion(IVirus virus) {
		Sick s=new Sick(this.age,this.location,this.settlement,virus,Simulation.Clock.now());
        this.settlement.removePerson(this);
        this.settlement.addPerson(s);
        return s;
	}
	
	public int getAge() {
    	return age;
    }
	
	public Point getLocation() {
    	return location;
    }
	
	public Settlement getSettlement() {
    	return settlement;
    }
	
	public double getDistance(Person p2)
	{
		int x1 = this.location.getX();
		int y1 = this.location.getY();
		int x2 = p2.location.getX();
		int y2 = p2.location.getY();
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	@Override
	public String toString() {
		return "Person Status:" + getClass().getSimpleName() + ", age:" + getAge() + 
				", Location:" + getLocation() + ", settlement:" + getSettlement();
	}

		// auto generated equals
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (age != other.age)
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (settlement == null) {
				if (other.settlement != null)
					return false;
			} else if (!settlement.equals(other.settlement))
				return false;
			return true;
		}

		public double getContagionProbability() {
			// TODO Auto-generated method stub
			return contagionProbability();
		}


		public long getContagiousTime() {
			// TODO Auto-generated method stub
			return 0;
		}


		public IVirus getVirus() {
			// TODO Auto-generated method stub
			return null;
		}
}
