package Location;

public class Location {
	
	private Point position;
	private Size size;

	public Location() {
		
	}
	
	public Location(Point position,Size size) {
		this.position=position;
		this.size=size;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

}
