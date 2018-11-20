package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;

import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public interface TestResult {
	
	/**
	 * Returns true if the test sentence can be formalised, false if there is no way to correctly formalise it.
	 * @return
	 */
	public boolean isPositiveInstance();
	
	
	/**
	 * Returns a number [0,1] which represents how often the result is used correctly
	 * @param r
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public double score(Result r) throws FileNotFoundException, IOException;
	
	/**
	 * Returns true if the result is correct
	 * @param r
	 * @return
	 */
	public static boolean isCorrect(Double r) {
		return r >= 0.99;
	}
	
	/**
	 * Returns true if the result is completely wrong
	 * @param r
	 * @return
	 */
	public static boolean isWrong(Double r) {
		return r < 0.99;
	}
	
	/**
	 * Returns true if the result is sometimes correct
	 * @param r
	 * @return
	 */
	public static boolean isSometimesCorrect(Double r) {
		return r > 0.01;
	}
	
	
}
