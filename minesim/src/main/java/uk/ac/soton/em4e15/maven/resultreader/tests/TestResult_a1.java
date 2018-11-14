package uk.ac.soton.em4e15.maven.resultreader.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.resultreader.Result;
import uk.ac.soton.em4e15.maven.resultreader.TestResult;
import uk.ac.soton.em4e15.maven.resultreader.TestResultNegative;
import uk.ac.soton.em4e15.maven.resultreader.TestResultPositive;

public class TestResult_a1 extends TestResultPositive{

	@Override
	public double scoreIteration(boolean positive, Result r) throws FileNotFoundException, IOException {
		Mine m = TestResult.getRandomMine();
		
		return 0;
	}

	

}
