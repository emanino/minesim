package uk.ac.soton.em4e15.maven.resultreader;

import java.io.IOException;

public abstract class TestResultNegative extends TestResultAbstract{

	
	@Override
	public boolean isPositiveInstance() {
		return false;
	}
	
	@Override
	public double score(Result r, EvaluationFile eval) throws IOException {
		double score = r.solutionFound() ? 0 : 1 ;
		eval.addEntry(r.assignment_id, r.stn, "["+score+"]", "["+score+"]");
		return score;
	}
	
}
