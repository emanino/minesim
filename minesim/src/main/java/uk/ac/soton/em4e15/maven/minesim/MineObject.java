package uk.ac.soton.em4e15.maven.minesim;

import java.util.Random;
import java.util.Set;

public interface MineObject {
	
	public Integer getId();
	public void update(Set<Action> actions, Random rand, MineState next);
}
