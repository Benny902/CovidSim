package UI;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import Country.Settlement;

public class MapPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void MapPanel() {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g; // for the quality
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!(UI.TopMenu.isLoaded())) { // if simulation has not been loaded yet
		////Test
		//System.out.println("\n\n\n*******\n Entered paintComponent 'Sim not loaded  \n******\n\n");
		////Test
		g.setColor(Color.black);
		g.drawString("Simulation has not been loaded yet", 10, 30);
		}
		
		else { // simulation has been loaded
			////Test
			//System.out.println("\n\n\n*******\n Entered paintComponent  'drawings'  \n******\n\n");
			////Test
			List<Settlement> temp = new ArrayList<Settlement>();
			temp = Country.Map.getSettlements();
			int settlmentsCount =temp.size();
			
			/*
			for (int i=0;i<settlmentsCount-1;i++) { // loop to draw the lines between ALL settlements (not what they asked)
				
				int centerPointX = temp.get(i).getCenterX();
				int centerPointY = temp.get(i).getCenterY();
				int centerPointX2 = temp.get(i+1).getCenterX();
				int centerPointY2 = temp.get(i+1).getCenterY();
				
				g.setColor(Color.black);
				g.drawLine(centerPointX, centerPointY, centerPointX2, centerPointY2);
				}
			*/
		
			List<String> connections = IO.SimulationFile.getConnections();
			int centerPointX=0;
			int centerPointY=0;
			for (int i=0;i<connections.size()-1;i+=2) {
				for  (int j=0;j<settlmentsCount;j++) {
					if (connections.get(i).toString().equals(temp.get(j).getName().toString())) { // found first sett
						for  (int k=0;k<settlmentsCount;k++) {
							if (connections.get(i+1).toString().equals(temp.get(k).getName().toString())) {
								g.setColor(Color.black);
								if (temp.get(j).getRamzorColor().getColor() != null) {
									Color clr = AverageRGB(temp.get(j).getRamzorColor().getColor(),temp.get(k).getRamzorColor().getColor());
									g.setColor(clr);
								}
								g.drawLine(temp.get(j).getCenterX(), temp.get(j).getCenterY(), 
											temp.get(k).getCenterX(), temp.get(k).getCenterY());
								g.drawLine(temp.get(j).getCenterX()+1, temp.get(j).getCenterY()+1, 
										temp.get(k).getCenterX()+1, temp.get(k).getCenterY()+1); // second drawLine is to make the line bigger
								}
							}
						}
					}			
				}
			
			for (int i=0;i<settlmentsCount;i++) { // loop to draw the settlements
				int x = temp.get(i).getLocation().getPosition().getX(); 
				int y = temp.get(i).getLocation().getPosition().getY(); 
				int width = temp.get(i).getLocation().getSize().getWidth();
				int height = temp.get(i).getLocation().getSize().getHeight();
				double precentage1 = temp.get(i).getListOfSick().size();
				double precentage2 =temp.get(i).getNumOfPeople();
				double precentage =(precentage1/precentage2); 
				
				if (precentage >= 0.8) // 
					g.setColor(Color.RED);
				else if(precentage >= 0.6 && precentage <0.8) // get settlement color
					g.setColor(Color.ORANGE);
				else if(precentage >= 0.4 && precentage <0.6) // get settlement color
					g.setColor(Color.YELLOW);
				else //if(precentage <0.4) // get settlement color
					g.setColor(Color.GREEN);
				
				g.fillRect(x, y, width, height);
				}
			
			for (int i=0;i<settlmentsCount;i++) { // loop to draw the settlements names
				centerPointX = temp.get(i).getLocation().getPosition().getX();
				centerPointY = temp.get(i).getCenterY();
				
				g.setColor(Color.black);
				g.drawString(temp.get(i).getName(), centerPointX+3, centerPointY);
				}
		}
		
	}
	
	private Color AverageRGB(Color color1, Color color2) {
		int R = (color1.getRed()+color2.getRed())/2;
		int G = (color1.getGreen()+color2.getGreen())/2;
		int B = (color1.getBlue()+color2.getBlue())/2;	
		Color clr = new Color(R, G, B);	
		return clr;
	}

	public Dimension getPreferredSize() {
	return new Dimension(400, 400);
	}
	
	public static JInternalFrame createFrame() {
		JInternalFrame frame = new JInternalFrame("");
		((javax.swing.plaf.basic.BasicInternalFrameUI)frame.getUI()).setNorthPane(null);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.add(new MapPanel());
		frame.pack();
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	
}
