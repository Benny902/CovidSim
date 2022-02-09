package UI;

  
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.*;  
public class JSpinnerWindow {  
	
	private static int theValue;
	
    public static int getTheValue() {
		return theValue;
	}

	public static void setTheValue(int theValue) {
		JSpinnerWindow.theValue = theValue;
	}

	public static void start() {

        JFrame f = new JFrame("JSpinner");
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(250, 100);
        SpinnerModel value = new SpinnerNumberModel(1,  //initial value  
        											0,  //minimum value  
        										   1000,  //maximum value  
        										    1); //step  
        JSpinner spinner = new JSpinner(value);
        spinner.setBounds(100, 100, 50, 30);
        f.add(spinner);
        f.add(label);
        f.setSize(300, 300);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                label.setText("Value : " + ((JSpinner) e.getSource()).getValue());
                int x = (int) ((JSpinner) e.getSource()).getValue();
                Simulation.Clock.setTicks_per_day(x);
                theValue =x;
            }
        }); 
}  
}  