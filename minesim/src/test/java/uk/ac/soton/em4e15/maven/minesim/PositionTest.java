package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;
import org.junit.Test;

public class PositionTest {
	
	@Test
	public void testPlus() {
		Position a = new Position(1.0, 1.0, 1.0);
		Position b = new Position(0.0, -1.0, 2.0);
		Position c = a.plus(b);
		
		assertEquals("Wrong x axis", Double.valueOf(1.0), c.getX());
		assertEquals("Wrong y axis", Double.valueOf(0.0), c.getY());
		assertEquals("Wrong z axis", Double.valueOf(3.0), c.getZ());
	}
	
	@Test
	public void testMinus() {
		Position a = new Position(1.0, 1.0, 1.0);
		Position b = new Position(0.0, -1.0, 2.0);
		Position c = a.minus(b);
		
		assertEquals("Wrong x axis", Double.valueOf(1.0), c.getX());
		assertEquals("Wrong y axis", Double.valueOf(2.0), c.getY());
		assertEquals("Wrong z axis", Double.valueOf(-1.0), c.getZ());
	}
	
	@Test
	public void testTimes() {
		Position a = new Position(1.0, 2.0, 3.0);
		Position b = a.times(3.0);
		
		assertEquals("Wrong x axis", Double.valueOf(3.0), b.getX());
		assertEquals("Wrong y axis", Double.valueOf(6.0), b.getY());
		assertEquals("Wrong z axis", Double.valueOf(9.0), b.getZ());
	}
	
	@Test
	public void testLength() {
		Position a = new Position(3.0, 4.0, 0.0);
		double length = a.length();
		
		assertEquals("Wrong length", Double.valueOf(5.0), Double.valueOf(length));
	}
	
	@Test
	public void testDistanceTo() {
		Position a = new Position(0.0, 1.0, 5.0);
		Position b = new Position(0.0, -4.0, -7.0);
		double dist = a.distanceTo(b);
		
		assertEquals("Wrong distance", Double.valueOf(13.0), Double.valueOf(dist));
	}
}
