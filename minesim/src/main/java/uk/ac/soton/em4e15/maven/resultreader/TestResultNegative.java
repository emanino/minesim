package uk.ac.soton.em4e15.maven.resultreader;

public abstract class TestResultNegative extends TestResultAbstract{

	
	@Override
	public boolean isPositiveInstance() {
		return false;
	}
	
	@Override
	public double score(Result r) {
		return r.solutionFound()? 0 : 1 ;
	}
	
}
