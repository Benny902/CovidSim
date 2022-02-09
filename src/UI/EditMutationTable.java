package UI;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.*;

public class EditMutationTable extends JFrame {

    private static final long serialVersionUID = 1L;
    public static JTable table;


    public EditMutationTable() {
    	 Object[] columnNames = {"                     ","ChineseVariant", "BritishVariant", "ShouthAfricanVariant"};
         Object[][] data = {
            {"ChineseVariant",                                false,             true,              true},
            {"BritishVariant",                                false,             false,             true},
            {"ShouthAfricanVariant",    false,                false,             false},
             
         };
         DefaultTableModel model = new DefaultTableModel(data, columnNames);
         table = new JTable(model) {

             private static final long serialVersionUID = 1L;

             //@Override
            @SuppressWarnings({ "unchecked", "rawtypes" })
			// public Class getColumnClass(int column) {
             //return getValueAt(0, column).getClass();
             //}
             @Override
             public Class getColumnClass(int column) {
                 switch (column) {
                     case 0:
                         return String.class;
                     case 1:
                     	return Boolean.class;
                     case 2:
                     	return Boolean.class;
                     case 3:
                     	return Boolean.class;
                     default:
                         return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
    }

    public static void main2() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	EditMutationTable frame = new EditMutationTable();
            	frame.setPreferredSize(new Dimension(600, 200));
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

	public static JTable getTable() {
		return table;
	}
    

	
   
}