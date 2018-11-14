package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class TestResultPositive extends TestResultAbstract implements TestResultPositiveInterface{

	@Override
	public double score(Result r) throws FileNotFoundException, IOException {
		double totScore = 0;
		// test cases when the predicate should trigger
		for(int i = 0; i  < TestResult.getIterations(); i++) {
			totScore += scoreIteration(true, r);
		}
		// test cases when the predicate should not trigger
		for(int i = 0; i  < TestResult.getIterations(); i++) {
			totScore += scoreIteration(false, r);
		}
		return totScore/TestResult.getIterations();
	}
	
	@Override
	public boolean isPositiveInstance() {
		return true;
	}
}
