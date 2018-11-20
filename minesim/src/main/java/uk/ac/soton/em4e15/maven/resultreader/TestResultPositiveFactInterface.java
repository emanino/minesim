package uk.ac.soton.em4e15.maven.resultreader;

import java.util.Set;

import uk.ac.soton.em4e15.maven.minesim.Mine;

public interface TestResultPositiveFactInterface extends TestResultPositiveInterface {

	public Set<String> expectedSet(Mine m);
}
