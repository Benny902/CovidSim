package IO;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import Country.Settlement;

public class StatisticsFile {

    public static File saveFileFunc(String fileName, JFrame staticticsWindow) {
	    FileDialog fd = new FileDialog(staticticsWindow, "Please choose a file:", FileDialog.SAVE);
	    fd.setFile(fileName);
	    fd.setVisible(true);

	    if (fd.getFile() == null)
	    	return null;
	    File f = new File(fd.getDirectory(), fd.getFile());
	    System.out.println(f.getPath());
	    return f;
	  }
	  
	public static void toExcel(JTable table, File file){
	    try{
		    TableModel model = table.getModel();
		    FileWriter excel = new FileWriter(file);
		
		    for(int i = 0; i < model.getColumnCount(); i++){
		        excel.write(model.getColumnName(i) + "\t");
		    }
		
		    excel.write("\n");
		
		    for(int i=0; i< model.getRowCount(); i++) {
		        for(int j=0; j < model.getColumnCount(); j++) {
		            excel.write(model.getValueAt(i,j).toString()+"\t");
		        }
		        excel.write("\n");
		    }
		
		    excel.close();
		
		}catch(IOException e){ System.out.println(e); }
	}

	public static void toText(Settlement s, File file) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		// OR: BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append)));
		
		//writer.write(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString()); //LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
		writer.write("Time: "+LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString()); //LocalTime.now().truncatedTo(ChronoUnit.SECONDS)");
		writer.newLine();
		
		writer.write("Settlement name: "+s.getName().toString()); // settlement name
		writer.newLine();
		
		writer.write("Current Sick count: "+String.valueOf(s.getListOfSick().size())); // current num of sickCount
		writer.newLine();
		
		writer.write("Current Deceased count: "+String.valueOf(s.getDeceased())); // current num of deceased
		writer.newLine();
		
		writer.write(" ");
		writer.newLine();
		
		writer.close();
	}
	public static void CreateEmptyLog(File file) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		writer.write(" "); 
		writer.newLine();
		writer.close();
	}
	
	
	
	

}
