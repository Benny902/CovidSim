package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import Country.Map;


public class SimulationFile implements Iterator {
	private static Map map;
	static List<String> connections;
	// ARRAY = [1,2,3,4,5,6,7,8]
	          
	 

	public static void setConnections(List<String> connections) {
		SimulationFile.connections = connections;
	}
	
	public static List<String> getConnections() {
		return connections;
	}

	public SimulationFile() throws IOException{
		map = new Map();
	}
	
	public static void reset() {
		map = new Map();
	}
	
	public Map startSim(File file) throws IOException {
		FileReader a = new FileReader(file);
		BufferedReader b = new BufferedReader(a);
		String tmp;
		connections = new ArrayList<String>();
		while ((tmp = b.readLine())!=null) {
		
			if (tmp.contains("#")) 
			{
				String[] txt = tmp.split(";"); // ("\\;")
				connections.add(txt[1]);
				connections.add(txt[2]);
			}
			else
			{
			String[] txt = tmp.split(";");
			map.makeMap(txt);
			}
		}
		a.close();
		b.close();
		return map;
	}
	
	public static String PickAFile() {
		 try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    }catch(Exception ex) {
		        ex.printStackTrace();
		    }
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "text file (*.txt)", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
        }
        return chooser.getSelectedFile().toString(); 
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		return null;
	}

	/////
	
}