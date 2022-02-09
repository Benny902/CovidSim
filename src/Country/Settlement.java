package Country;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Location.Location;
import Location.Point;
import Location.Size;
import Population.Person;
import Population.Sick;

public class Settlement {

	private String name;
	private Location location;
	private List<Person> people;
	RamzorColor ramzorColor;
	private int numOfPeople;
	private int Deceased=0;
	private int logCalls=1;
	private int settlementCapacity;
	private int numOfVaccines=0;
	private int numOfVaccinatedPeople=0;
	
	public int getNumOfVaccinatedPeople() {
		return numOfVaccinatedPeople;
	}
	public void setNumOfVaccinatedPeople(int numOfVaccinatedPeople) {
		this.numOfVaccinatedPeople = numOfVaccinatedPeople;
	}

	private static Settlement[] settlement;
	private List<Sick> ListOfSick;
	private List<Person> ListOfNotSick;
	//settlement,setlmentName,X,Y,W,H,numOfPPL
	
	public Settlement(String name,Location location,int numOfPeople) { // default
        this.name = name;
        this.location=location;
        this.people=new ArrayList<Person>();
        this.ramzorColor=RamzorColor.Green;
        this.setNumOfPeople(numOfPeople);
        this.settlementCapacity=(int) (numOfPeople*1.3);
	}
	

	public Location getLocation() {
		return location;
	}
	
	public int getSettlementCapacity() {
		return settlementCapacity;
	}


	public void addNumOfVaccines(int numOfVaccines) {
		this.numOfVaccines += numOfVaccines;
	}


	public int getCenterX()
	{
		int x = location.getPosition().getX(); 
		int width = location.getSize().getWidth();
		width=width/2;
		return x+width;
	}
	
	public int getCenterY()
	{
		int y = location.getPosition().getY(); 
		int height = location.getSize().getHeight();
		height=height/2;
		return y+height;
	}
	
	double contagiousPercent() {
		double count = 0;
		double numOfPeople = this.getNumOfPeople();
		for (int i =0;i<numOfPeople;i++)
		{
			if (this.getPeople().get(i) instanceof Sick)
			count++;
		}
		
		double cPercent = (double)(count/numOfPeople);
		return cPercent;
	}
	
	Point randomLocation() {
		Size sizeOfSettlement =this.location.getSize();
		int w = sizeOfSettlement.getWidth();
		int h = sizeOfSettlement.getHeight();
		w = (int) (Math.random()*w);
		h = (int) (Math.random()*h);
		return new Point(h,w);
	}
	
	public boolean addPerson(Person p) {
		// need add function.. (array list etc)
		this.people.add(p);
        return true;
	}
	public boolean removePerson(Person p) {
		// need add function.. (array list etc)
		this.people.remove(p);
        return true;
	}
	
	  public List<Sick> getListOfSick(){
	    	ListOfSick=new ArrayList<Sick>();
	    	for(int i=0;i<this.getNumOfPeople();i++) {
	    		if (this.getPeople().get(i) instanceof Sick) {
	    			ListOfSick.add((Sick)this.getPeople().get(i));
	    		}
	    	}
	    	return ListOfSick;
	    }
	
	public static boolean transferPerson(Person p,Settlement toSettlement){
		
		if (toSettlement.getNumOfPeople()>=toSettlement.getMaxNumofPeople()) // if Capacity of settlement is full return false
			return false;
		
		// for second IF condition, by Asiignment 2 (im not sure i did this part right)
		double d = toSettlement.getRamzorColor().getScore();
		double x = p.contagionProbability()*Math.min(1, 0.14 * Math.pow(Math.E,2-0.25*d));
		double random = Math.random() *  1;
		if (x >= random) { 
			return true;
		}
		
		// if both if's fail
		return false;
	}

	public int getMaxNumofPeople() {
		return settlementCapacity;
	}


	public int getNumOfVaccines() {
		return numOfVaccines;
	}
	public void setNumOfVaccines(int numOfVaccines) {
		this.numOfVaccines = numOfVaccines;
	}


	public Settlement[] getSettlement() {
		return settlement;
	}

	
	public List<Person> getListOfNotSick() {
		return ListOfNotSick;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	/*/// To delete later
	  
	  Green(0.4),
	Yellow(0.6),
	Orange(0.8),
	Red(1.0);
	  
	  */
	
	public RamzorColor calculateRamzorGrade() {
		double P=this.contagiousPercent();
		double x=0,y=0,z=0,w=0;
		double C=0;
		double c = this.ramzorColor.getScore();
		if (this.getClass().getSimpleName().equals("Moshav")){
			
			x=(double) Math.pow(1.2, c);
			y=(double) (P-0.35);
			z=(double)x*y;
			w=(double) Math.pow(z,5);
			C= (double)(0.3+3*(w));
			//C = 0.3 + 3*(Math.pow(((Math.pow(1.2, c))*(P-0.35)),5));
		}

		if (this.getClass().getSimpleName().equals("Kibbutz")) {
			
			x=(double) Math.pow(1.5, c);
			y=(double) (P-0.4);
			z=(double)x*y;
			w=(double) Math.pow(z,3);
			C= (double)(0.45+(w));
			//C = 0.45 + Math.pow(((Math.pow(1.5, c))*(P-0.4)),3);
		}
		
		if (this.getClass().getSimpleName().equals("City")) {
			C = 0.2 * Math.pow(4, (1.25*P));
		}
			
	
		
		if (C<=0.4) // green
		{
			this.ramzorColor = RamzorColor.Green;
			this.setRamzorColor(RamzorColor.Green);
			this.ramzorColor.setColor(Color.GREEN);
		}
		
		else if (C>0.4 && C<=0.6) // Yellow
		{
			this.ramzorColor = RamzorColor.Yellow;
			this.setRamzorColor(RamzorColor.Yellow);
			this.ramzorColor.setColor(Color.YELLOW);
		}
		
		else if (C>0.6 && C<=0.8) // Orange
		{
			this.ramzorColor = RamzorColor.Orange;
			this.setRamzorColor(RamzorColor.Orange);
			this.ramzorColor.setColor(Color.ORANGE);
		}
		
		else //if (C>0.8) // Red
		{
			this.ramzorColor = RamzorColor.Red;
			this.setRamzorColor(RamzorColor.Red);
			this.ramzorColor.setColor(Color.RED);
		}
		
		return this.ramzorColor;
	}
	


	@Override
	public String toString() {
		return "Settlement [name=" + name + ", location=" + location + 
				", people=" + people + 
				"ramzorColor=" + ramzorColor + ", numOfPeople=" + numOfPeople + "]";
	}


	public RamzorColor getRamzorColor() {
		return ramzorColor;
	}


	public void setRamzorColor(RamzorColor ramzorColor) {
		this.ramzorColor = ramzorColor;
	}


	public int getNumOfPeople() {
		return this.numOfPeople;
	}
	
	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}
	
	public List<Person> getPeople(){
		return people;
	}
	public int getDeceased() {
		return Deceased;
	}
	public void setDeceased(int deceased) {
		Deceased = deceased;
	}
	public int getLogCalls() {
		return logCalls;
		
	}
	public void setLogCalls(int logCalls) {
		this.logCalls=logCalls;
		
	}
	
}
