package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.junit.Test;

public class TunnelTest {

	@Test
	public void testConstructor() throws FileNotFoundException, IOException {
		
		// create a state
		Properties prop = new Properties();
		prop.load(new FileInputStream("minesim.properties"));
		MineState state = new MineState(0, prop);
		
		Position head = new Position(0, 0, 0);
		Position tail = new Position(2, 2, 2);
		
		Tunnel tunnel = new Tunnel(state, head, tail, 4, new LayoutAtomStatus(), 1.0);
		
		List<Integer> atoms = tunnel.getAtomList();
		Set<Integer> neighboursHead = ((LayoutAtom) state.getObject(atoms.get(0))).getNeighbours();
		Set<Integer> neighboursSecond = ((LayoutAtom) state.getObject(atoms.get(1))).getNeighbours();
		Set<Integer> neighboursTail = ((LayoutAtom) state.getObject(tunnel.getLastAtom())).getNeighbours();
		
		assertEquals("Wrong number of LayoutAtoms", 4, atoms.size());
		assertEquals("Wrong number of neighbours for LayoutAtom 0", 1, neighboursHead.size());
		assertEquals("Wrong number of neighbours for LayoutAtom 1", 2, neighboursSecond.size());
		assertEquals("Wrong number of neighbours for LayoutAtom 3", 1, neighboursTail.size());
		assertTrue("Wrong connection between LayoutAtoms 0 and 1", neighboursHead.contains(atoms.get(1)));
		assertTrue("Wrong connection between LayoutAtoms 1 and 0", neighboursSecond.contains(atoms.get(0)));
		assertTrue("Wrong connection between LayoutAtoms 1 and 2", neighboursSecond.contains(atoms.get(2)));
		assertTrue("Wrong connection between LayoutAtoms 3 and 2", neighboursTail.contains(atoms.get(2)));
	}
}
