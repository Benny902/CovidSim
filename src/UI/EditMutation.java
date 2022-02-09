package UI;

import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.Frame;



import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTable;







public class EditMutation extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditMutation(Frame window) {
	
		//super(window, "Main Window", true);
			//final JDialog frame = new JDialog(window, "EditMutation window" , true); // true = modal.  false = not modal
			JFrame frame = new JFrame("EditMutation Window");
			frame.setPreferredSize(new Dimension(600, 300));
			BorderLayout myBorderLayout = new BorderLayout();
			myBorderLayout.setHgap(10);
			myBorderLayout.setVgap(10);
			frame.setLayout(myBorderLayout);
			
			//JTable myTable = new JTable();
			UI.EditMutationTable.main2();
			JTable myTable = UI.EditMutationTable.getTable();
			frame.getContentPane().add(new JScrollPane(myTable));
			//frame.add(myTable);
			
	
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(false);
	}

}
