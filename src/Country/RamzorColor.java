package Country;

import java.awt.Color;

public enum RamzorColor {
	Green(0.4),
	Yellow(0.6),
	Orange(0.8),
	Red(1.0);
	
	double score;
	private Color color;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	RamzorColor() { // green by default
		this.score = 0.4;
		this.color=Color.GREEN;
	}
	
	RamzorColor(double score) {
		//this.score = score;
	}

	public double getScore() {
		if(this==Green) {
			return 0.4;
		}
		else if(this==Yellow) {
			return 0.6;
		}
		else if(this==Orange) {
			return 0.8;
		}
		else {
			return 1.0;
		}
	}

	public void setScore(double score) {
		this.score = score;
	}
}
