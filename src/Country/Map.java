package Country;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import IO.SimulationFile;
import Location.Location;
import Location.Point;
import Location.Size;
import MyMonitor.TheMonitor;
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import UI.MapPanel;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Map  implements Runnable, Iterator  {
	
	public int iteratorIndex;
	
	private static ArrayList<Settlement> settlements;
	private static int count=0;
	static SimulationFile simFile;
	public static Map map;
	static MapPanel mapDraw;
	public static boolean playFlag=false; // for the play
	public static boolean pauseFlag=false; // for the pause
	public static int sliderSpeed=1;
	
	public final int CurrentThread;
	public static int settlementCount=0;
	
	
	public static ArrayList<Thread> threads;
	
	public Map() {	
		super();
		this.CurrentThread = 0;
		//settlements = new ArrayList<Settlement>();
	}
	
	public Map(int x) {	
		super();
		this.CurrentThread =x;
		//settlements = new ArrayList<Settlement>();
	}
	
	public static Map getMap() {
		return map;
	}
	
	public static int getSliderSpeed() {
		return sliderSpeed;
	}

	public static void setSliderSpeed(int sliderSpeed) {
		Map.sliderSpeed = sliderSpeed;
	}
	public static boolean isShouldRun() {
		return playFlag;
	}

	public static void setShouldRun(boolean shouldRun) {
		Map.playFlag = shouldRun;
	}

	public static void setMap(Map map) {
		Map.map = map;
	}
	
	public static ArrayList<Settlement> getSettlements() {
		return settlements;
	}

	public static void setSettlements(ArrayList<Settlement> settlements) {
		Map.settlements = settlements;
	}
	
//reset everything before a new run, (if the program is stopped with the "Stop" button, this function will "reset" everything for the next new run.	
	public static void resetStuff() throws IOException {
		count=0;
		Simulation.Clock.setTicks_per_day(1);
		Simulation.Clock.setTimeNow(1);
		settlementCount =0;
		IO.SimulationFile.setConnections(null);
		threads = new ArrayList<Thread>();
		map = new Map();
		IO.SimulationFile.reset();
		simFile = new SimulationFile();
		settlements = new ArrayList<Settlement>();
	}

//func to load the initialization file 
	public static void FileLoad(String[] args) throws IOException{
		
		resetStuff(); //reset stuff (for new runs after the Stop button)
		
		//1. Load step
		//Get the location of the upload file and load the entire map. 
		//simFile = new SimulationFile(); //
		//map = simFile.startSim(new File(Avoda1 + "temp.txt")); // this is before the GUI
		map = simFile.startSim(new File(IO.SimulationFile.PickAFile()));
	
		map.printMap();
		System.out.println("Initial File Loaded Successfully");
		UI.MainWindow.mapPanel.repaint();	
	}
	
//func to make the map from the loaded file.
	public void makeMap(String[] txt) {
		
		Location location = new Location(new Point(Integer.parseInt(txt[2]),Integer.parseInt(txt[3])), new Size(Integer.parseInt(txt[4]),Integer.parseInt(txt[5])));
		if (txt[0].equals("Moshav")) {
			settlements.add(new Moshav(txt[1],location,Integer.parseInt(txt[6])));
			addPopulation(count++);
			threads.add(new Thread(new Country.Map(settlementCount)));				
			settlementCount++;
		}
		else if (txt[0].equals("City")) {
			settlements.add(new City(txt[1],location,Integer.parseInt(txt[6])));
			addPopulation(count++);
			//threads.add(new Thread());
			threads.add(new Thread(new Country.Map(settlementCount)));	
			settlementCount++;
		}
		else {//(txt[0].equals("Kibbutz"))
			settlements.add(new Kibbutz(txt[1],location,Integer.parseInt(txt[6])));
			addPopulation(count++);
			//threads.add(new Thread());
			threads.add(new Thread(new Country.Map(settlementCount)));	
			settlementCount++;
		}
		
		
	}
	
//add population func (to makeMap)	
	public void addPopulation(int i) {
		for(int j =0; j<settlements.get(i).getNumOfPeople();j++) {
			Person p = new Healthy(randomAgeGaussian(), settlements.get(i).randomLocation(),settlements.get(i));
			settlements.get(i).addPerson(p);
		}
	}
	
// func to for addPopulation, to add them in random places in the map.	
	public static int randomAgeGaussian() {
		Random randomAge = new Random();
		double x = (double)((randomAge.nextGaussian()));
		for(;(x < -1 || x > 1);) {
			x = (double)((randomAge.nextGaussian()));
		}
		x*=6;
		x+=9;
		return (int)(5*x+((double)(Math.random()*5)));	
	}
	
// simulation thread
	
	@Override
	public void run() { 
		
		
		
			set1PercentSick_2(CurrentThread);
			
			for (int i=0;i<5;i++)
			{
				tryToContaminate_2(6,CurrentThread); // //going through all the settlements, choosing each sick person for which for him 
															//we will choose randomly 6 people from the same settlement and try to contagion them
															//do this whole simulation 5 times.
			}
			
			UI.MainWindow.mapPanel.repaint();
			UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel());
			//trySleepBySlider();
			
			
			
			while(playFlag) { // while not Stopped
			/// from here we start the simulation functions of assignment 2:
				trySleepBySlider();
				try {
					UI.TopMenu.pauser.look();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//trySleepBySlider();
				
				for (int i=0;i<Simulation.Clock.getTicks_per_day();i++) {   //getting ticks (that set by spinner / or default 1)
					SecondAssignmentSimulation();  
					/*
					if (i==0) 
						Simulation.Clock.nextTick(); //++timeNow only once per 'for' loop
					*/
					//map.printMap();//  print after all simulations are done 
					//UI.MainWindow.mapPanel.repaint(); // repaint the map after each simulation
					//UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel()); // update the statistics after each simulation		
					
					///*PRINTING ALL THESE^ IN CLOCK THREAD*///
					trySleep(100);
				}
				UI.MainWindow.mapPanel.repaint();
				 // infinite loop while on pause, so will "rest" 1second*infinity until shouldRun2 will become true again (by pressing play)
			}
			
		
	}
	
	
public synchronized void set1PercentSick_2(int i){ 
	for (int j=0;j<(settlements.get(i).getNumOfPeople()/100);j++) { // /100= 1% of population
		Random r = new Random();
		int x=r.nextInt(settlements.get(i).getNumOfPeople());
		RandomVariant(settlements.get(i).getPeople().get(x));
	}
	settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
	settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
}
	
//sleep shortcut func	(sleep by slider speed)
	public static void trySleepBySlider()
	{
		try {
			Thread.sleep(1000/sliderSpeed); // the bigger the sliderSpeed value, it makes the sleep number smaller for less delay
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//sleep shortcut func (sleep by sended num time)
	public static void trySleep(int num)
	{
		try {
			Thread.sleep(num); // the bigger the sliderSpeed value, it makes the sleep number smaller for less delay
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
// load functions from First Assignment	
	public static void FirstAssignmentSimulation (int numOfRuns, int numOfRandomPeople)
	{	
		//this is the simulation of from asiigngment 1: 
		
		map.set1PercentSick(); // set 1 percent of the whole population sick (1% of each settlement) , by assignment1
		UI.MainWindow.mapPanel.repaint();
		map.printMapDetails();
		//trySleep();
		
		for (int i=0;i<numOfRuns;i++)
		{
			map.tryToContaminate(numOfRandomPeople); // //going through all the settlements, choosing each sick person for which for him 
														//we will choose randomly 6 people from the same settlement and try to contagion them
														//do this whole simulation 5 times.
		}
		map.printMap();//  print after all simulations are done 
		UI.MainWindow.mapPanel.repaint();
	}
	
// set 1 percent of the whole population sick (1% of each settlement) , by assignment1
	public synchronized void set1PercentSick(){ 
		for (int i =0;i<settlements.size();i++) {
			for (int j=0;j<(settlements.get(i).getNumOfPeople()/100);j++) { // /100= 1% of population
				Random r = new Random();
				int x=r.nextInt(settlements.get(i).getNumOfPeople());
				RandomVariant(settlements.get(i).getPeople().get(x));
			}
			settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
			settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color	
		}
	}
	
//Random variant (for setSick)
	public static void RandomVariant(Person p) {
		Random r=new Random();
		int x = r.nextInt(3);
		if (x==0) {
			p.contagion(new ChineseVariant());
		}
		else if (x==1) {
			p.contagion(new BritishVariant());
		}
		else {
			p.contagion(new SouthAfricanVariant());
		}
	}
		
// tryToContaminate (by all the sick) numOfRandompeople, sent 6  by assignment1
	public synchronized void tryToContaminate(int numOfRandomPeople) { 
		int j=0;
		for(int i=0;i<settlements.size();i++) {
			List <Sick> sickArr=settlements.get(i).getListOfSick();
			for(j=0;j<sickArr.size();j++) {
				IVirus virus = sickArr.get(j).getVirus();
				for(int k=0;k<numOfRandomPeople;k++) {
					Random r = new Random();
					int x=r.nextInt(settlements.get(i).getNumOfPeople());
					virus.tryToContagion(sickArr.get(j), settlements.get(i).getPeople().get(x));
				}
			}
			settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
			settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
			
		}
	}
	
	public void tryToContaminate_2(int numOfRandomPeople, int i) { 
		int j=0;
		
			List <Sick> sickArr=settlements.get(i).getListOfSick();
			for(j=0;j<sickArr.size();j++) {
				IVirus virus = sickArr.get(j).getVirus();
				for(int k=0;k<numOfRandomPeople;k++) {
					Random r = new Random();
					int x=r.nextInt(settlements.get(i).getNumOfPeople());
					virus.tryToContagion(sickArr.get(j), settlements.get(i).getPeople().get(x));
				}
			}
			settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
			settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
	}
	
// load functions from Second Assignment	(& Third)
	public  void SecondAssignmentSimulation() // those are the Assignment2 functions.
	{	
		map.tryToContaminateBy20(3,CurrentThread);  // First function: try to contaminate 3 random people by 20% of the sick 
		//fyi, for 3, the contamination is Super slow.
		
		map.makeConvalescent(25,CurrentThread);  //second function: in every settlement, the ones who are sick more than 25 days, make them Convalescent.
		
		map.tryToTransferToRandomSettlement(3,CurrentThread); // send the % number you want to try to transfer
		
		map.VaccinateHealthyPeople(CurrentThread); // fourth function: if theres healthy people in a settlement, and vaccines available in this settlement, vaccinate them, and update the vaccinesNum
	
		map.tryToKillAllSicks(CurrentThread); // assignment 3 : function to try and kill each sick
		
		try {
			Thread.sleep(100); // the bigger the sliderSpeed value, it makes the sleep number smaller for less delay
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			map.checkIfNeedToLog(CurrentThread);  // assignment 3 : function to output log.txt file when 1% of population in a settlement dies
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

// tryToContaminate (by 20% of the sick) numOfRandompeople , sent 3  by assignment2
	public synchronized void tryToContaminateBy20(int numOfRandomPeople, int i) {  
		
		List <Sick> sickArr=settlements.get(i).getListOfSick();
		
		for(int j=0;j<(sickArr.size()*0.2);j++) { //size*0.2 = 20% from the sick. if theres under 5 people sick the size will be 0!! so i add +1
			IVirus virus = sickArr.get(j).getVirus();
			for(int k=0;k<numOfRandomPeople;k++) {
				Random r = new Random();
				int x=r.nextInt(settlements.get(i).getNumOfPeople());
				virus.tryToContagion(sickArr.get(j), settlements.get(i).getPeople().get(x));
			}
		}
		settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
		settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
	}
	
//in every settlement, the ones who are sick more than moreThanDaysSickNum days (by assignment sending 25) -> make them Convalescent.
// exampele: 3 is the day he got contaminated, 22 is the day now, so: IF  (3+22 >= 25)  Do.
	public synchronized void makeConvalescent(int moreThanDaysSickNum, int i) {
		for (int j=0;j<(settlements.get(i).getNumOfPeople());j++) { 
			if (settlements.get(i).getPeople().get(j) instanceof Sick) { // if this person is sick
				if (settlements.get(i).getPeople().get(j).getContagiousTime()+Simulation.Clock.getTimeNow() >=moreThanDaysSickNum) { // if conTime >=25
					 Convalescent c = new Convalescent(settlements.get(i).getPeople().get(j).getAge(),
												   	   settlements.get(i).getPeople().get(j).getLocation(),
													   settlements.get(i).getPeople().get(j).getSettlement(),
													   settlements.get(i).getPeople().get(j).getVirus());
					 settlements.get(i).removePerson(settlements.get(i).getPeople().get(j));
					 settlements.get(i).addPerson(c);
				}
			}
		settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
		settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color	
		}		
	}
	
//if theres healthy people in a settlement, and vaccines available in this settlement, vaccinate them, and update the vaccinesNum
	public synchronized void VaccinateHealthyPeople(int i){
		
		if (settlements.get(i).getNumOfVaccines() > 0) // if theres vaccines available
			for(int k = 0;k<settlements.get(i).getNumOfPeople();k++) {
				for (int j=0;j<settlements.get(i).getNumOfVaccines();j++) { 
					if (settlements.get(i).getPeople().get(j) instanceof Healthy) { // if this person is Healthy
				//Vaccinated(int age, Point location,Settlement settlement, long vacctinationTime)
						Vaccinated v = new Vaccinated(settlements.get(i).getPeople().get(j).getAge(),
							   	   settlements.get(i).getPeople().get(j).getLocation(),
								   settlements.get(i).getPeople().get(j).getSettlement(),
								   Simulation.Clock.getTimeNow());
						settlements.get(i).removePerson(settlements.get(i).getPeople().get(j));
						settlements.get(i).addPerson(v);
						settlements.get(i).setNumOfVaccines(settlements.get(i).getNumOfVaccines()-1); // update current vaccinesNum -1
						settlements.get(i).setNumOfVaccinatedPeople(settlements.get(i).getNumOfVaccinatedPeople()+1); // update current to num to +1
					}
		settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
		settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color	
				}
			}
	}
	
//try to transfer num(3%) from each settlement population, a person to another settlement
	public synchronized void tryToTransferToRandomSettlement(int num, int i){
		double numPrecent = (double)(num*0.01);
		for (int j=0;j<settlements.get(i).getNumOfPeople()*numPrecent;j++) { 
			Random r = new Random();
			int x=r.nextInt(settlements.size());
			if(Settlement.transferPerson(settlements.get(i).getPeople().get(j),settlements.get(x))) { // if "transferPerson" returns true
				settlements.get(x).addPerson(settlements.get(i).getPeople().get(j)); // add this person to the new settlement
				settlements.get(x).setNumOfPeople(settlements.get(x).getNumOfPeople()+1); // update numOfppl to num to +1
				settlements.get(i).removePerson(settlements.get(i).getPeople().get(j)); //  remove this person from his old settlement
				settlements.get(i).setNumOfPeople(settlements.get(i).getNumOfPeople()-1); // update numOfppl to num to -1
			}
		settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
		}	
	}
	
//assignment 3 : function to try and kill each sick
	public synchronized void tryToKillAllSicks(int i) {
		for (int j=0;j<settlements.get(i).getNumOfPeople();j++) { 
			if (settlements.get(i).getPeople().get(j) instanceof Sick) { // if this person is sick
				if (settlements.get(i).getPeople().get(j).getVirus().tryToKill((Sick) settlements.get(i).getPeople().get(j))) { // if true = tryToKil killed him.
					settlements.get(i).setDeceased(settlements.get(i).getDeceased()+1); // Deceased count++
					settlements.get(i).removePerson(settlements.get(i).getPeople().get(j)); // delete this person from the settlement
					settlements.get(i).setNumOfPeople(settlements.get(i).getNumOfPeople()-1); // update numOfppl to num to -1
				}
			}
		}
	}
	
// check if 1% of population from a settlement is deceased, if yes output log file.	
	public synchronized void checkIfNeedToLog(int i) throws IOException {
		if (settlements.get(i).getDeceased()>=((settlements.get(i).getNumOfPeople()/100)*settlements.get(i).getLogCalls())) { // if deceased number is Higher than population/100 (deceased precentage is atleast 1%).
			// why am i multiplying by settlements.get(i).getLogCalls() ? ===> because after every log on this settlement, next time i will check for 2% 3% and so on...
			IO.StatisticsFile.toText(settlements.get(i), UI.TopMenu.file); // send this settlement to text output function., with file path
			settlements.get(i).setLogCalls(settlements.get(i).getLogCalls()+1); // logCalls++
		}
	}
	
////////////////StatisticsWindow JButtons
// for the Button in the statistics window to to add X vaccines
	public static void addVaccinesButtonFunction(String settlementChose, int num) {
		for (int i =0;i<settlements.size();i++) {
			if(settlements.get(i).getName().toString().equals(settlementChose)) { // if we are in the right place.
				settlements.get(i).addNumOfVaccines(num);
			}
		}
	}
	
// for the Button in the statistics window to set 0.1%sick
	public static void Set01PercentSick(String settlementChose) { 
		for (int i =0;i<settlements.size();i++) {
			if(settlements.get(i).getName().toString().equals(settlementChose)) { // if we are in the right place.
				int size=settlements.get(i).getNumOfPeople()/1000; //1000= 0.1%
				if (size==0) // if population is under 1000 so 0.1% population is under 1 and the size will be 0!!
					size+=1; // so i add +1
				for (int j=0;j<size;j++) { 
					Random r = new Random();
					int x=r.nextInt(settlements.get(i).getNumOfPeople());
					RandomVariant(settlements.get(i).getPeople().get(x));
					settlements.get(i).calculateRamzorGrade();
				}
			}	
			settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
			settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
		}
	}
	
// added a button to set 10%sick (becuase 0.01 is too slow for some tests)
	public static void Set10PercentSick(String settlementChose) {
		for (int i =0;i<settlements.size();i++) {
			if(settlements.get(i).getName().toString().equals(settlementChose)) { // if we are in the right place.
				for (int j=0;j<(settlements.get(i).getNumOfPeople()/10);j++) { // /10= 10% of population 
					Random r = new Random();
					int x=r.nextInt(settlements.get(i).getNumOfPeople());
					RandomVariant(settlements.get(i).getPeople().get(x));
					settlements.get(i).calculateRamzorGrade();
				}
			}	
			settlements.get(i).setRamzorColor(settlements.get(i).calculateRamzorGrade());; // updates for each settl for its color
			settlements.get(i).calculateRamzorGrade(); // updates for each settl for its color
		}	
	}

//// print only basic details
	public void printMap() {  
		int i=0,sick=0,total=0;
		int[] arrSettlementPop = new int[settlements.size()];
		int[] arrSettlementSick = new int[settlements.size()];
		for (i =0;i<settlements.size();i++) {
			total+=settlements.get(i).getNumOfPeople();
			for (int j=0;j<settlements.get(i).getNumOfPeople();j++) {
				//System.out.println(settlements.get(i).getPeople().get(j).toString());  --> To print without the people details
				arrSettlementPop[i]++;
				if (settlements.get(i).getPeople().get(j).getClass().getSimpleName().equals("Sick")) {
					arrSettlementSick[i]++;
					sick++;
				}
			}
		}
		System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		for (i =0;i<settlements.size();i++) {
			double precentage1 =arrSettlementSick[i];
			double precentage2 =arrSettlementPop[i];
			double precentage =(precentage1/precentage2);
			System.out.println("* Live update from  "+settlements.get(i).getName()
					+ " \t-    Population: " +arrSettlementPop[i]
							+". \tSick count: "+arrSettlementSick[i]
									+",  ("+String.format("%.1f", precentage*100)
										+"%). \tRamzor Score: "+String.format("%.2f", precentage)+".");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("* Total Population of all settlements: " +total);
		System.out.println("* Total Sick of all settlements: " +sick);
		System.out.println("\n* Days passed: " +Simulation.Clock.getTimeNow() +" .");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
	}
	
////print basic details + the WHOLE population details.
	public void printMapDetails() {  
		int i=0,sick=0,total=0;
		int[] arrSettlementPop = new int[settlements.size()];
		int[] arrSettlementSick = new int[settlements.size()];
		for (i =0;i<settlements.size();i++) {
			total+=settlements.get(i).getNumOfPeople();
			for (int j=0;j<settlements.get(i).getNumOfPeople();j++) {
				System.out.println(settlements.get(i).getPeople().get(j).toString());
				arrSettlementPop[i]++;
				if (settlements.get(i).getPeople().get(j).getClass().getSimpleName().equals("Sick")) {
					arrSettlementSick[i]++;
					sick++;
				}
			}
		}
		System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		for (i =0;i<settlements.size();i++) {
			double precentage1 =arrSettlementSick[i];
			double precentage2 =arrSettlementPop[i];
			double precentage =(precentage1/precentage2);
			System.out.println("* Live update from: "+settlements.get(i).getName()
					+ " \t:: Population: " +arrSettlementPop[i]
							+". \tSick count: "+arrSettlementSick[i]
									+",  ("+String.format("%.1f", precentage*100)
										+"%). \tRamzor Score: "+String.format("%.2f", precentage)+".");
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("* Total Population of all settlements: " +total);
		System.out.println("* Total Sick of all settlements: " +sick);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
	}

@Override
public boolean hasNext() {
	if(iteratorIndex < settlements.size()){
        return true;
     }
     return false;
}

@Override
public Object next() {
	  if(this.hasNext()){
          return settlements.get(iteratorIndex++);
       }
       return null;
}
	
}
