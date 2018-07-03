package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class LayoutAtomTest {
	
	@Test
	public void testConstructor() {
		
		MineState state = new MineState(0);
		
		LayoutAtom a = new LayoutAtom(0, new Position(0, 0, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom b = new LayoutAtom(0, new Position(1, 0, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom c = new LayoutAtom(0, new Position(2, 0, 0), state, new LayoutAtomStatus(), 1.1);
		
		Set<Integer> neighboursA = a.getNeighbours();
		Set<Integer> neighboursB = b.getNeighbours();
		Set<Integer> neighboursC = c.getNeighbours();
		
		assertTrue("LayoutAtoms a and b should be connected", neighboursA.contains(b.getId()));
		assertTrue("LayoutAtoms a and c should not be connected", !neighboursA.contains(c.getId()));
		assertTrue("LayoutAtoms b and a should be connected", neighboursB.contains(a.getId()));
		assertTrue("LayoutAtoms b and c should be connected", neighboursB.contains(c.getId()));
		assertTrue("LayoutAtoms c and a should not be connected", !neighboursC.contains(a.getId()));
		assertTrue("LayoutAtoms c and b should be connected", neighboursC.contains(b.getId()));
	}
	
	@Test
	public void testUpdate() {
		
		MineState oldState = new MineState(0);
		MineState newState = new MineState(1);
		
		LayoutAtom a = new LayoutAtom(0, new Position(1.0, 2.0, 3.0), oldState, new LayoutAtomStatus(), 1.0);
		a.update(new HashSet<Action>(), new Random(), newState);
		
		Set<MineObject> objects = newState.getObjects();
		assertEquals("Failed to insert a copy of the LayoutAtom in the new state", 1, objects.size());
		
		MineObject b = newState.getObject(a.getId());
		assertTrue("Mismatching ids between the old and new copy of the LayoutAtom", b != null);
	}

	@Test
	public void testShortestPathTo() {
		
		MineState state = new MineState(0);
		
		LayoutAtom a = new LayoutAtom(0, new Position(0, 0, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom b = new LayoutAtom(0, new Position(1, 0, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom c = new LayoutAtom(0, new Position(2, 0, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom d = new LayoutAtom(0, new Position(2, 1, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom e = new LayoutAtom(0, new Position(2, 2, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom f = new LayoutAtom(0, new Position(1, 2, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom g = new LayoutAtom(0, new Position(0, 2, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom h = new LayoutAtom(0, new Position(0, 3, 0), state, new LayoutAtomStatus(), 1.1);
		LayoutAtom i = new LayoutAtom(0, new Position(0, 1, 0), state, new LayoutAtomStatus(), 1.1);
		
		Path path1 = b.shortestPathTo(new HashSet<Integer>(Arrays.asList(h.getId())), new HashSet<Integer>());
		Path path2 = b.shortestPathTo(new HashSet<Integer>(Arrays.asList(h.getId())), new HashSet<Integer>(Arrays.asList(i.getId())));
		Path path3 = b.shortestPathTo(new HashSet<Integer>(Arrays.asList(h.getId())), new HashSet<Integer>(Arrays.asList(g.getId())));
		
		assertEquals("Failed to find the shortest path", Arrays.asList(b.getId(), a.getId(), i.getId(), g.getId(), h.getId()), path1.getAtomIds());
		assertEquals("Failed to find the alternative path", Arrays.asList(b.getId(), c.getId(), d.getId(), e.getId(), f.getId(), g.getId(), h.getId()), path2.getAtomIds());
		assertEquals("Failed to spot that the target is unreachable", null, path3.getAtomIds());
	}
}
