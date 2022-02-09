package UI;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Country.Settlement;

public class StatisticsWindow {
  protected JFrame window;
  JFrame frame;
  public static JTable table = new JTable();
  public static JTable getTable() {
	return table;
}
  
  static TableModel model;



String settlementChose;
  

public static TableModel createModel() {
	 List<Settlement> temp = new ArrayList<Settlement>();
		temp = Country.Map.getSettlements();
		int settlmentsCount =temp.size();
		String [][] arrayOfRows = new String[settlmentsCount][8];
	    for(int i=0 ;i<settlmentsCount;i++) {
	    	arrayOfRows[i][0]=(temp.get(i).getName()); // settlement name
	    		arrayOfRows[i][1]=(String.valueOf(temp.get(i).getClass().getSimpleName())); // stype
	    		arrayOfRows[i][2]=(String.valueOf(temp.get(i).getRamzorColor())); // s color
	    		double precentage1 =temp.get(i).getListOfSick().size();
				double precentage2 =temp.get(i).getNumOfPeople();
				double precentage =(precentage1/precentage2);
	    		arrayOfRows[i][3]=(String.valueOf(String.format("%.1f", precentage*100)+" %")); // s sickPrecentage
	    		arrayOfRows[i][4]=(String.valueOf(temp.get(i).getNumOfVaccines())); // s vaccines available
	    		arrayOfRows[i][5]=(String.valueOf(temp.get(i).getNumOfVaccinatedPeople())); // s vaccinated // 
	    		arrayOfRows[i][6]=(String.valueOf(temp.get(i).getDeceased())); // s Deceased  
	    		arrayOfRows[i][7]=(String.valueOf(temp.get(i).getNumOfPeople())); // s population num  	
	    }
	    String columns[] = {"Name","Type","RamzorColor","SickPrecentage","Vaccines","Vaccinated","Deceased","Population"};
	    
	    model = new DefaultTableModel(arrayOfRows, columns) {
	      /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int column) {
	        Class returnValue;
	        if ((column >= 0) && (column < getColumnCount())) {
	          returnValue = getValueAt(0, column).getClass();
	        } else {
	          returnValue = Object.class;
	        }
	        return returnValue;
	      }
	    };
	return model;
}

public StatisticsWindow() {
    JFrame frame = new JFrame("Statistics Window");
	
	String[] names = {"Name","Type","RamzorColor","SickPrecentage","Vaccines","Vaccinated","Deceased","Population"};
	JComboBox<String> jcBox;
	jcBox= new JComboBox<String>();
	for (String name : names) {
		jcBox.addItem(name);
	}
	
	jcBox.addActionListener((ActionListener) new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == jcBox) {
	    		switch (jcBox.getItemAt(jcBox.getSelectedIndex())) {
	    		case "Name" : 
	    			table.getRowSorter().toggleSortOrder(0);
	    			break;
	    		case "Type" : 
	    			table.getRowSorter().toggleSortOrder(1); 
	    			break;
	    		case "RamzorColor" :
	    			table.getRowSorter().toggleSortOrder(2); 
	    			break;
	    		case "SickPrecentage" :
	    			table.getRowSorter().toggleSortOrder(3); 
	    			break;
	    		case "Vaccines" :
	    			table.getRowSorter().toggleSortOrder(4); 
	    			break;
	    		case "Vaccinated" :
	    			table.getRowSorter().toggleSortOrder(5); 
	    			break;
	    		case "Deceased" :
	    			table.getRowSorter().toggleSortOrder(6);  
	    			break;
	    		case "Population" :
	    			table.getRowSorter().toggleSortOrder(7);  
	    			break;
	    		}
	    	}

	    } 
	});
	JPanel northPanel = new JPanel();
	northPanel.add(jcBox, BorderLayout.NORTH); // this needs to be JComboBox
	

	 // creating "model"  here
    
    table = new JTable(createModel());
    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
    table.setRowSorter(sorter);
    JScrollPane pane = new JScrollPane(table);
    frame.add(pane, BorderLayout.CENTER);
    
   
    
/////////////// table mouse click listener (to add 0.1% sick or to add vaccines)////
    
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount() == 1) {
                final JTable target = (JTable)e.getSource();
                final int row = target.getSelectedRow();
                //final int column = target.getSelectedColumn();
                // Cast to ur Object type
                settlementChose = (String)target.getValueAt(row, 0);
                //System.out.println(settlementChose);
                // TODO WHAT U WANT!
            }
        }
    });
    
///////////////    
    
    
    //JPanel panel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("Filter");
    northPanel.add(label, BorderLayout.WEST);
    final JTextField filterText = new JTextField("Type here a Filter word.");
    northPanel.add(filterText, BorderLayout.NORTH);
    frame.add(northPanel, BorderLayout.NORTH);
    JButton button = new JButton("Filter");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String text = filterText.getText();
        if (text.length() == 0) {
          sorter.setRowFilter(null);
        } else {
          sorter.setRowFilter(RowFilter.regexFilter(text));
        }
      }
    });
    northPanel.add(button);
    frame.add(northPanel, BorderLayout.NORTH);
    
    
    JPanel southPanel = new JPanel();
	southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
  
	
////////	
	JButton Save; // Map Button 
	Save = new JButton("Save");
	Save.addActionListener(new ActionListener() {
		@Override
    public void actionPerformed(ActionEvent e) {
			File file = IO.StatisticsFile.saveFileFunc("statistics.xls",window);
			IO.StatisticsFile.toExcel(table, file);
			//((AbstractTableModel) model).fireTableDataChanged();
    	}
    });
	southPanel.add(Save);
///////////////////	
	
	JButton AddSick; // Add Sick Button
	AddSick = new JButton("Add Sick (0.01%)");
	AddSick.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
			Country.Map.Set01PercentSick(settlementChose);
			UI.MainWindow.mapPanel.repaint(); // repaint the map after each simulation
			UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel()); // update the statistics after each simulation
	    	}
	    });
	southPanel.add(AddSick);
	
	JButton AddSick2; // Add Sick Button
	AddSick2 = new JButton("Add Sick (10%)");
	AddSick2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
			Country.Map.Set10PercentSick(settlementChose);
			UI.MainWindow.mapPanel.repaint(); // repaint the map after each simulation
			UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel()); // update the statistics after each simulation
	    	}
	    });
	southPanel.add(AddSick2);
	
	
	
	
	JButton Vaccinate; // Vaccinate Button
	Vaccinate = new JButton("Vaccinate");
	Vaccinate.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    	String input = JOptionPane.showInputDialog("Enter number of Vaccines to add");
	    	int num = Integer.parseInt(input);
			Country.Map.addVaccinesButtonFunction(settlementChose,num);
			UI.MainWindow.mapPanel.repaint(); // repaint the map after each simulation
			UI.StatisticsWindow.table.setModel(UI.StatisticsWindow.createModel()); // update the statistics after each simulation
	    	}
	    });
	southPanel.add(Vaccinate);
	
	
	
	
	frame.add(southPanel, BorderLayout.SOUTH);
	frame.pack();
	frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(900, 300);
    frame.setVisible(true);
  }



}




