package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.rdf4j.query.TupleQueryResult;

import uk.ac.soton.em4e15.maven.minesim.Mine;

public interface TestResultPositiveInterface {

	public double scoreIteration(boolean positive, Result r) throws FileNotFoundException, IOException;

	/**
	 * Return true if the sentence to formalise is an if-then question
	 * @return
	 */
	public boolean isIfThen();
	
	public double evaluateQueryResults(Mine m, TupleQueryResult result, Result r);
	
	/**
	 * Checks whether a mine complies with a certain condition
	 * @param positive whether to return true if the condition holds, or if it doesn't
	 * @param m
	 * @return
	 */
	public boolean conditionHolds(boolean positive, Mine m);
}
