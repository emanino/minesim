package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TestResultPositiveInterface {

	public double scoreIteration(boolean positive, Result r) throws FileNotFoundException, IOException;
}
