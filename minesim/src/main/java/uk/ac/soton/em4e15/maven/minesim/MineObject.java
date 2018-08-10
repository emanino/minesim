package uk.ac.soton.em4e15.maven.minesim;
import java.util.List;
//
import java.util.Random;
import java.util.Set;

import org.apache.jena.graph.Triple;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public interface MineObject {
	
	public Integer getId();
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next);
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next);
	public Set<Triple> getSensorInfoRDF();
	public String toJsonGui();
}
