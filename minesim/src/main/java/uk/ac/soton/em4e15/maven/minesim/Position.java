package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class Position {
	
	private Double x;
	private Double y;
	private Double z;
	
	Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	Position(List<Double> coordinates) {
		this(coordinates.get(0), coordinates.get(1), coordinates.get(2));
	}
	
	Position(Position pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public Double getX() {
		return x;
	}
	
	public Double getY() {
		return y;
	}
	
	public Double getZ() {
		return z;
	}
	
	public List<Double> getXYZ() {
		return Arrays.asList(x, y, z);
	}
	
	public Position plus(Position pos) {
		return new Position(x + pos.getX(), y + pos.getY(), z + pos.getZ());
	}
	
	public Position minus(Position pos) {
		return new Position(x - pos.getX(), y - pos.getY(), z - pos.getZ());
	}
	
	public Position times(double factor) {
		return new Position(x * factor, y * factor, z * factor);
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public double distanceTo(Position pos) {
		return pos.minus(this).length();
	}
	
	public String toJsonGui() {
		return "[" + x + "," + y + "," + z + "]";
	}
}
