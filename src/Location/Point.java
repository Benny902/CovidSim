package Location;

//import java.util.Scanner;

public class Point {

	private int x;
	private int y;
	
	public Point() {
		x=y=0;
	}
	
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
    public void setY(int y) {
    	this.y=y;
	}
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    //useless main for now
	public static void main(String[] args) {
		//System.out.println("Test");
	}

}
