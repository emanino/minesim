package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;

import uk.ac.soton.em4e15.maven.resultreader.EvaluationFile;
import uk.ac.soton.em4e15.maven.resultreader.Result;
import uk.ac.soton.em4e15.maven.resultreader.TestResult;

public class ScoreIterationRunnable implements Runnable {

	public TestResultPositive tr;
	public Result r;
	public Boolean positive;
	public Double result;
	
	
	public ScoreIterationRunnable(TestResultPositive tr, boolean positive, Result r) {
		this.tr = tr;
		this.r = r;
		this.positive = positive;
		result = 0.0;
	}
	@Override
	public void run() {
		try {
			result = tr.scoreIteration(positive, r);
		} catch (Exception e) {
			throw new RuntimeException("Repackaged Exception:\n"+e.getMessage());
		}
	}
}
