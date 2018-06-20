package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class MineTest {

	@Test
	public void testConstructor() {
		
		// temporary test
		Mine mine = new Mine(0, 0);
		MineState state = mine.getState();
		
		// count the number of LayoutAtoms
		int nAtoms = 0;
		for(MineObject obj: state.getObjects())
			if(obj instanceof LayoutAtom)
				++nAtoms;
		
		assertEquals("Unexpected number of LayoutAtoms", 44, nAtoms);
	}

}
