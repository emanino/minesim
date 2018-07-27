package uk.ac.soton.em4e15.maven.minesim;
//
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;

import org.junit.Test;

public class MineTest {

	@Test
	public void testConstructor() throws FileNotFoundException, IOException {
		// create a mine
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 3; j++) {
				Properties prop = new Properties();
				prop.load(new FileInputStream("minesim.properties"));
				Mine mine = new Mine(prop, i, j);
				
				// count the number of LayoutAtoms
				MineState state = mine.getState();
				int nAtoms = 0;
				for(MineObject obj: state.getObjects())
					if(obj instanceof LayoutAtom)
						++nAtoms;
				
				//assertEquals("Unexpected number of LayoutAtoms", 184, nAtoms);
			}
		}
	}
	
	@Test
	public void testUpdate() throws FileNotFoundException, IOException {
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 4; j++) {
				// create a mine
				Properties prop = new Properties();
				prop.load(new FileInputStream("minesim.properties"));
				Mine mine = new Mine(prop, i, j);
				
				for(int t = 0; t < 100; ++t) {		
					mine.update(new HashSet<UserAction>());
				}
			}
		}
		
	}
	
	@Test
	public void testToJsonGui() throws FileNotFoundException, IOException {
		// create a mine
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 3; j++) {
				Properties prop = new Properties();
				prop.load(new FileInputStream("minesim.properties"));
				Mine mine = new Mine(prop, i, j);
				String json = mine.toJsonGui();
				System.out.println(json);
				//PrintWriter out = new PrintWriter("mine"+i+".json");
				//out.println(json);
				//out.close();
			}
		}
	}
}
