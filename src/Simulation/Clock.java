package Simulation;

import java.io.IOException;

import Country.Map;
import MyMonitor.Pauser;

public class Clock implements Runnable{
	
	private static Clock instance = null;
	public static Pauser pauser=new Pauser();
	
	private static long timeNow=1;
	private static int ticks_per_day=1;
	
	public static void setTimeNow(long timeNow) {
		Clock.timeNow = timeNow;
	}

	private Clock(Pauser pauser) {
		this.pauser=pauser;
	}
	
	@Override
	public void run() {
		
		 while(Country.Map.playFlag){
		       try {
				pauser.look();
				try {
					Thread.sleep(1000/Map.sliderSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(100*Simulation.Clock.getTicks_per_day());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Map.map.printMap();//  print after all simulations are done 
				UI.MainWindow.mapPanel.repaint(); // repaint the map after each simulation
				UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel()); // update the statistics after each simulation		
				Thread.sleep(100);
				timeNow++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
	  
	}

	 public static Clock getInstance() throws IOException {

		  if (instance == null) {
		   instance = new Clock(pauser);
		  }

		  return instance;
		 }
	
	public static long getTimeNow() {
		return timeNow;
	}
	
	public static long now()
	{
		return timeNow;
	}
	
	public static void nextTick() {
		timeNow++;
	}

	public static  void setTicks_per_day(int ticks_per_day) {
		Clock.ticks_per_day = ticks_per_day;
	}
	
	public static int getTicks_per_day() {
		return ticks_per_day;
	}

	public static long daysPassed(long startTime)
	{
		// today - startTime 
		// to round it up = (x + y - 1) / y
		return (timeNow + startTime - 1) / startTime;
	}


}
