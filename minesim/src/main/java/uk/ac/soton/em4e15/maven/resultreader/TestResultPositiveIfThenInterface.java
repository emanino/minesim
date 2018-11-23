package uk.ac.soton.em4e15.maven.resultreader;

import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import uk.ac.soton.em4e15.maven.minesim.Mine;

public interface TestResultPositiveIfThenInterface extends TestResultPositiveInterface {

	public Pair<Set<String>,String> expectedSet(Mine m);
	
}
