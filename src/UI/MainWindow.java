package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JInternalFrame mapPanel;
	
	final static JSlider slider = new JSlider(1,101,1);
	
	 public static void changeSpeed(int x)
     {
		 System.out.println("Changed simulation Speed to " +x);
         Country.Map.setSliderSpeed(x);
     }
	
	public static void startMainWindow(){
		
///////////////// Main Frame /////////	
		JFrame frame = new JFrame("Main Window"); // Main window
		BorderLayout myBorderLayout = new BorderLayout();
		frame.setPreferredSize(new Dimension(500, 500)); // mainwindow start size
		myBorderLayout.setHgap(10); 
		myBorderLayout.setVgap(10);
		frame.setLayout(myBorderLayout); // mainwindow gaps
	
		
//////////////////// Top Menu ///////////
		JMenuBar menuBar; // Menu bar
		menuBar = UI.TopMenu.TopMenu();
		frame.setJMenuBar(menuBar); // setting the Menu bar into he mainwindow
		
		
////////// Map panel draw ///////////////
		mapPanel = new JInternalFrame(); 
		//((javax.swing.plaf.basic.BasicInternalFrameUI)frame2.getUI()).setNorthPane(null);
		mapPanel=UI.MapPanel.createFrame();
		frame.getContentPane().add(mapPanel);
		
		
//////////////////// JSlider //////////////		
		
	    slider.setPreferredSize(new Dimension(100, 50));
	    slider.setPaintTicks(true);
	    
	      slider.setPaintLabels(true);
	      slider.setMajorTickSpacing(10);
	      slider.setMinorTickSpacing(1);
	      //addSlider(slider, "Snap to ticks");
	      
	     
	      
	    slider.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent event) {
	        //int value = slider.getValue();
	        //changeSpeed(value);
	        changeSpeed(slider.getValue()); 
	      }
	    });
	    frame.add(slider, BorderLayout.SOUTH); // Simulation Speed Jslider
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
		
		
		frame.pack();
		frame.setLocationRelativeTo(null); // make the window in center of the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
