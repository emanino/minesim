package uk.ac.soton.em4e15.maven.minesim;
//
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class LayoutAtomTest {
	
	@Test
	public void testConstructor() throws FileNotFoundException, IOException {
		
		// create a state
		Properties prop = new Properties();
		prop.load(new FileInputStream("WebContent/WEB-INF/minesim.properties"));
		MineState state = new MineState(0, prop);
		
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
	public void testUpdate() throws FileNotFoundException, IOException {
		
		// create a two consecutive states
		Properties prop = new Properties();
		prop.load(new FileInputStream("WebContent/WEB-INF/minesim.properties"));
		MineState oldState = new MineState(0, prop);
		MineState newState = new MineState(1, prop);
		MineObjectScheduler scheduler = new MineObjectScheduler(oldState, prop);
		
		LayoutAtom a = new LayoutAtom(0, new Position(1.0, 2.0, 3.0), oldState, new LayoutAtomStatus(), 1.0);
		a.update(scheduler, new Random(), newState);
		
		Collection<MineObject> objects = newState.getObjects();
		assertEquals("Failed to insert a copy of the LayoutAtom in the new state", 1, objects.size());
		
		MineObject b = newState.getObject(a.getId());
		assertTrue("Mismatching ids between the old and new copy of the LayoutAtom", b != null);
	}

	@Test
	// Layout schema:
	//  0 1 2 3
	//0 a b c
	//1 i   d
	//2 g f e
	//3 h
	public void testShortestPathTo() throws FileNotFoundException, IOException {
		
		// create a state
		Properties prop = new Properties();
		prop.load(new FileInputStream("WebContent/WEB-INF/minesim.properties"));
		MineState state = new MineState(0, prop);
		
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

	@Test
	// Layout schema:
	//  0 1 2 3 4 5
	//0       a   o
	//1       b   h
	//2 s r q c p m
	//3   z   d   l
	//4 t u v e   k
	//5   y   f   j
	//6   x w g h i
	public void testShortestPathTo2() throws FileNotFoundException, IOException {
		
		// create a state
		Properties prop = new Properties();
		prop.load(new FileInputStream("WebContent/WEB-INF/minesim.properties"));
		MineState state = new MineState(0, prop);
		
		LayoutAtom a = new LayoutAtom(0, new Position(3, 0, 0), state, new LayoutAtomStatus(), 1.1); // 0
		LayoutAtom b = new LayoutAtom(0, new Position(3, 1, 0), state, new LayoutAtomStatus(), 1.1); // 1
		LayoutAtom c = new LayoutAtom(0, new Position(3, 2, 0), state, new LayoutAtomStatus(), 1.1); // 2
		LayoutAtom d = new LayoutAtom(0, new Position(3, 3, 0), state, new LayoutAtomStatus(), 1.1); // 3
		LayoutAtom e = new LayoutAtom(0, new Position(3.05, 4, 0), state, new LayoutAtomStatus(), 1.1); // 4
		LayoutAtom f = new LayoutAtom(0, new Position(3, 5, 0), state, new LayoutAtomStatus(), 1.1); // 5
		LayoutAtom g = new LayoutAtom(0, new Position(3, 6, 0), state, new LayoutAtomStatus(), 1.1); // 6
		LayoutAtom h = new LayoutAtom(0, new Position(4, 6, 0), state, new LayoutAtomStatus(), 1.1); // 7
		LayoutAtom i = new LayoutAtom(0, new Position(5, 6, 0), state, new LayoutAtomStatus(), 1.1); // 8
		LayoutAtom j = new LayoutAtom(0, new Position(5, 5, 0), state, new LayoutAtomStatus(), 1.1); // 9
		LayoutAtom k = new LayoutAtom(0, new Position(5, 4, 0), state, new LayoutAtomStatus(), 1.1); // 10
		LayoutAtom l = new LayoutAtom(0, new Position(5, 3, 0), state, new LayoutAtomStatus(), 1.1); // 11
		LayoutAtom m = new LayoutAtom(0, new Position(5, 2, 0), state, new LayoutAtomStatus(), 1.1); // 12
		LayoutAtom n = new LayoutAtom(0, new Position(5, 1, 0), state, new LayoutAtomStatus(), 1.1); // 13
		LayoutAtom o = new LayoutAtom(0, new Position(5, 0, 0), state, new LayoutAtomStatus(), 1.1); // 14
		LayoutAtom p = new LayoutAtom(0, new Position(4, 2, 0), state, new LayoutAtomStatus(), 1.1); // 15
		LayoutAtom q = new LayoutAtom(0, new Position(2, 2, 0), state, new LayoutAtomStatus(), 1.1); // 16
		LayoutAtom r = new LayoutAtom(0, new Position(1, 2, 0), state, new LayoutAtomStatus(), 1.1); // 17
		LayoutAtom s = new LayoutAtom(0, new Position(0, 2, 0), state, new LayoutAtomStatus(), 1.1); // 18
		LayoutAtom t = new LayoutAtom(0, new Position(0, 4, 0), state, new LayoutAtomStatus(), 1.1); // 19
		LayoutAtom u = new LayoutAtom(0, new Position(1, 4, 0), state, new LayoutAtomStatus(), 1.1); // 20
		LayoutAtom v = new LayoutAtom(0, new Position(2, 4, 0), state, new LayoutAtomStatus(), 1.1); // 21
		LayoutAtom w = new LayoutAtom(0, new Position(2, 6, 0), state, new LayoutAtomStatus(), 1.1); // 22
		LayoutAtom x = new LayoutAtom(0, new Position(1, 6.02, 0), state, new LayoutAtomStatus(), 1.1); // 23
		LayoutAtom y = new LayoutAtom(0, new Position(1, 5, 0), state, new LayoutAtomStatus(), 1.1); // 24
		LayoutAtom z = new LayoutAtom(0, new Position(1, 3, 0), state, new LayoutAtomStatus(), 1.1); // 25
		
		//  0 1 2 3 4 5
		//0      [a] [o]
		//1       b   h
		//2 s r q c p m
		//3  <z>  d   l
		//4 t u v e   k
		//5   y   f   j
		//6   x w g h i
		//Path path1 = z.shortestPathTo(new HashSet<Integer>(Arrays.asList(a.getId(), o.getId())), new HashSet<Integer>());
		//assertEquals("Failed to find the shortest path", Arrays.asList(z.getId(), r.getId(), q.getId(), c.getId(), b.getId(), a.getId()), path1.getAtomIds());
		
		//  0 1 2 3 4 5
		//0      [a] [o]
		//1       b   h
		//2 s r q # p m
		//3  <z>  d   l
		//4 t u v #   k
		//5   y   f   j
		//6   x w g h i
		//Path path2 = z.shortestPathTo(new HashSet<Integer>(Arrays.asList(a.getId(), o.getId())), new HashSet<Integer>(Arrays.asList(c.getId(), e.getId())));
		//assertEquals("Failed to find the shortest path", Arrays.asList(z.getId(), u.getId(), y.getId(), x.getId(), w.getId(), g.getId(), h.getId(), i.getId(), 
		//		j.getId(), k.getId(), l.getId(), m.getId(), n.getId(), o.getId()), path2.getAtomIds());
		
		//  0 1 2 3 4 5
		//0      [a] [o]
		//1       b   h
		//2[s]r q c p m
		//3   z   d   l
		//4[t]u v e   k
		//5   y   f   j
		//6  <x>w g h i
		Path path3 = x.shortestPathTo(new HashSet<Integer>(Arrays.asList(a.getId(), o.getId(), s.getId(), t.getId())), new HashSet<Integer>());
		assertEquals("Failed to find the shortest path", Arrays.asList(x.getId(), y.getId(), u.getId(), t.getId()), path3.getAtomIds());
			
		//  0 1 2 3 4 5
		//0      [a] [o]
		//1       b   h
		//2[s]r q c p m
		//3   z   d   l
		//4[t]# v e   k
		//5   y   f   j
		//6  <x>w g h i
		Path path4 = x.shortestPathTo(new HashSet<Integer>(Arrays.asList(a.getId(), o.getId(), s.getId(), t.getId())), new HashSet<Integer>(Arrays.asList(u.getId())));
		assertEquals("Failed to find the shortest path", Arrays.asList(x.getId(), w.getId(), g.getId(), f.getId(), e.getId(), d.getId(), c.getId()
				, b.getId(), a.getId()), path4.getAtomIds());
	}
}
