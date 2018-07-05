package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;

public class MineTest {

	@Test
	public void testConstructor() {
		
		// temporary test
		Mine mine = new Mine(1, 0);
		MineState state = mine.getState();
		
		// count the number of LayoutAtoms
		int nAtoms = 0;
		for(MineObject obj: state.getObjects())
			if(obj instanceof LayoutAtom)
				++nAtoms;
		
		assertEquals("Unexpected number of LayoutAtoms", 184, nAtoms);
	}
	
	@Test
	public void testUpdate() {
		
		// temporary test
		Mine mine = new Mine(1, 0);
		
		for(int t = 0; t < 2; ++t)
			mine.update(new HashSet<Action>());
	}
	
	@Test
	public void testToJsonGui() {
		
		Mine mine = new Mine(1, 0);
		String json = mine.toJsonGui();
		
		System.out.println(json);
	}
}
