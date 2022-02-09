package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Country.Map;
import MyMonitor.Pauser;
import MyMonitor.TheMonitor;
import Simulation.Clock;





public class TopMenu {
	private static boolean loaded = false;
	public static boolean isLoaded() {
		return loaded;
		
	}

	private static StatisticsWindow sw;
	private static EditMutation em;
	public static JInternalFrame cf;
	public static Thread start;
	
	public static Pauser pauser=new Pauser();
	public static Pauser pauser2=new Pauser();
	
	public static int nextSettlement=0;
	

	public static File file=new File("log.log"); // saving the log default to
	public static File file2=new File("log.log");
	
	protected static JFrame window;
	
	
	public static JMenuBar TopMenu() {
		
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();
		
///////////////////////////////FILE MENU BUTTON/////////////////////////////// 
		menu = new JMenu("File");
		menuBar.add(menu);

		////// Load a File button ////// 
		menuItem = new JMenuItem("Load a file");
		menuItem.addActionListener((ActionListener) new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	if (!loaded) {
			        try {
			        	Country.Map.FileLoad(null);
			        	loaded =true;
			        	IO.StatisticsFile.CreateEmptyLog(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		        else 
		        	JOptionPane.showMessageDialog(null, "Simulation FILE already loaded, you need to stop the simulation to Load a file");
		        	//System.out.println("Simulation already started, you need to cancel it to run a new one");
		    } 
		});
		menu.add(menuItem);
	
		
		////// Open Statistics Window ////// 
		menuItem = new JMenuItem("Statistics");
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loaded) {
		       //sw = new UI.StatisticsWindow(null);
		       sw = new UI.StatisticsWindow();
		      
				}
				else 
					JOptionPane.showMessageDialog(null, "Simulation FILE has not been loaded yet.");
					//System.out.println("Simulation has not been loaded yet");
		    }
		});
		menu.add(menuItem);

		////// Open Edit Mutations Window ////// 
		menuItem = new JMenuItem("Edit Mutation");
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loaded) {
					 em = new UI.EditMutation(null);	
					}		
				else 
					JOptionPane.showMessageDialog(null, "Simulation FILE has not been loaded yet.");
					//System.out.println("Simulation has not been loaded yet");
			}
		});
		menu.add(menuItem);
		
	////// The Log Save Location Button for assignment 3 :: Choose a **path** for the file that will be saved later ////// 
			menuItem = new JMenuItem("Log Save Location");
			menuItem.setMnemonic(KeyEvent.VK_B);
			menuItem.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					file2 = IO.StatisticsFile.saveFileFunc("log.log",window);
					file.renameTo(file2);
					//IO.StatisticsFile.toExcel(table, file);
				}
			});
			menu.add(menuItem);
			
		////// The Log Save Location Button for assignment 4 :: Choose a **path** for the file that will be saved later ////// 
			menuItem = new JMenuItem("Restore Log File location");
			menuItem.setMnemonic(KeyEvent.VK_B);
			menuItem.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "The saved location is: "+ file2.getPath().toString());
				}
			});
			menu.add(menuItem);
					
		
		////// Exit button to exit the finish the program ////// 
		menuItem = new JMenuItem("Exit");
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Exited Program. \nGood Bye.");
				JOptionPane.showMessageDialog(null, "Exited Program. \nGood Bye.");		
				System.exit(0);
				}
		});
		menu.add(menuItem);
		
		
/////////////////////////////// SIMULATION MENU BUTTON ////////////////////////JEKO The King!/////////// 
		menu = new JMenu("Simulation");
		menuBar.add(menu);

		
	////// Play button ////// 	
			menuItem = new JMenuItem("Play");
			menuItem.addActionListener((ActionListener) new ActionListener() {
			    public void actionPerformed(ActionEvent arg0) {
			    	if (loaded==true) {  // ((loaded is initialized with False!.) so here: if not loaded can enter second if
			    		if(Country.Map.playFlag==false) //((playFlag is initialized with False!.) if not playFlag we didnt start play yet.
			    		{  			
			    			Country.Map.playFlag=true;
			    			Country.Map.pauseFlag=true;
			    			for(int i =0 ; i<Country.Map.settlementCount;i++) { // starting settlements threads
			    				Country.Map.threads.get(i).start();
			    			}
			    			new Thread(new TheMonitor(pauser)).start(); // starting monitor thread (to pause/play etc)
			    			//new Thread(new Clock(pauser2)).start(); // starting the clock thread to ++day after each simulation loop
			    			Clock clock = null;
							try {
								clock = Clock.getInstance();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			new Thread(clock).start();
			    		}
			    		else if (Country.Map.playFlag==true) {// playFlag already became true => that means we are after play
			    			//Country.Map.pauseFlag=true;
			    			pauser.resume();
			    			//pauser2.resume();
			    			Clock.pauser.resume();
			    		}	
			    	}
			    	else // if a file havent been loaded yet
			        	JOptionPane.showMessageDialog(null, "Simulation FILE has not been loaded yet.");
			        	//System.out.println("Simulation already started, you need to cancel it to run a new one");
			    } 
			});
			menu.add(menuItem);
	
		
		////// Pause button ////// 
		menuItem = new JMenuItem("Pause");
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loaded) {
					if(Country.Map.playFlag) { // ((( sidenote for me :  assgn 2: it was pauseFlag))))
						//Country.Map.shouldRun2=false;
						pauser.pause();	
						//pauser2.pause();	
						Clock.pauser.pause();
					}		
					else 
						JOptionPane.showMessageDialog(null, "Simulation not in play mode");
				}
				else 
					JOptionPane.showMessageDialog(null, "Simulation has not been loaded yet.");
					//System.out.println("Simulation has not been loaded yet");
		    }
		});
		menu.add(menuItem);

		////// Stop button ////// 
		menuItem = new JMenuItem("Stop");
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loaded) {
					Country.Map.playFlag=false; // stopping the simulation.
					//Country.Map.pauseFlag=false;
					loaded = false;		
					}
				else 
					JOptionPane.showMessageDialog(null, "Simulation has not been loaded yet.");
					//System.out.println("Simulation has not been loaded yet");
		    }
		});
		menu.add(menuItem);
		
		////// Set Ticks Per Day button ////// 
		menuItem = new JMenuItem("Set Ticks Per Day");
		menuItem.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UI.JSpinnerWindow.start();
		    }
		});
		menu.add(menuItem);
		
		
	
////////////////////////////// HELP MENU BUTTON  ////////////////////////////////  
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		
		////// Help-> Help button /////
		menuItem = new JMenuItem("Help");
		menuItem.addActionListener((ActionListener) new ActionListener() {
		public void actionPerformed(ActionEvent arg0) { // is modal
			JOptionPane.showMessageDialog(null, "\n  \tHello, welcome to my assignment.\n\n"
				+ " +to be able to start the simulation you have to load a file first\n\n"
				+ "	+to Start a simulation, +Click on Simulation ->  Play\n\n"
				+ "	+to Pause the current simulation and make it on hold, +Click on Simulation -> Pause \n\n"
				+ "	+to Stop the current simulation entirely, +Click on Simulation -> Stop \n\n"
				+ "	+Please let me know if you need any assistance to view the program/code\n"
				+ " you can contact me at: Benny902@gmail.com  \n\n"
				+ " \tThank you.");
		} 
		});
		menu.add(menuItem);
		
		////// Help-> About button ////// 
		menuItem = new JMenuItem("About");
		menuItem.addActionListener((ActionListener) new ActionListener() {
		public void actionPerformed(ActionEvent arg0) { // non modal
			JOptionPane.showMessageDialog(null, "    ***Assignment 4*** \r\n"
					+ "    \r\n"
					+ "	Name: Benny Shalom\r\n"
					+ "	ID: 203500780\r\n\n"
					+ "	Name: Anthony Eitan Fleysher\r\n"
					+ "	ID: 203192331\r\n"
					+ "	\r\n"
					+ "	Date of the Assignment: 19/04/2021 ~ 10/06/2021\r\n"
					+ "");
		}
		});
		menu.add(menuItem);	
		
		return menuBar;
	}
	
}
