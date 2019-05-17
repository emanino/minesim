package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.http.HTTPQueryEvaluationException;
import org.eclipse.rdf4j.rio.RDFFormat;

import logic.ExternalDB;
import logic.PredicateEvaluation;
import logic.RDFUtil;
import logic.TextTemplate;
import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a0;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a4;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b4;

public abstract class TestResultPositive extends TestResultAbstract implements TestResultPositiveInterface{

	@Override
	public double score(Result r, EvaluationFile eval) throws FileNotFoundException, IOException {
		List<Double> posScore = new LinkedList<Double>();
		List<Double> negScore = new LinkedList<Double>();
		double totScore = 0;
		if(! r.solutionFound()) return 0;
		if(isIfThen() && (r.getIfBlock().size() == 0 || r.getThenBlock().size() == 0)) return 0;
		if(!isIfThen() && (r.getIfBlock().size() != 0 || r.getThenBlock().size() != 0)) return 0;
		// test cases when the predicate should trigger
		System.out.println(r);
		for(int i = 0; i  < TestResultUtil.getIterations(); i++) {
			double score = scoreIterationTimelimited(true, r);
			totScore += score;
			posScore.add(score);
			System.out.println("  POS "+totScore);
		}
		// test cases when the predicate should not trigger
		for(int i = 0; i  < TestResultUtil.getIterations(); i++) {
			double score =  scoreIterationTimelimited(false, r);
			totScore += score;
			negScore.add(score);
			System.out.println("  POS+NEG "+totScore);
		}
		eval.addEntry(r.assignment_id, r.stn, posScore, negScore);
		System.out.println("  TOT: ... ...  "+(totScore/(TestResultUtil.getIterations()*2) > 0.9 ? 1 : 0));
		return totScore/(TestResultUtil.getIterations()*2);
 		//return totScore/(TestResultUtil.getIterations()*2) > 0.9 ? 1 : 0;
	}
	
	@Override
	public boolean isPositiveInstance() {
		return true;
	}
	
	private static TimeUnit timeout_unit = TimeUnit.MINUTES;
	private static int timeout_threshold = 10;
	
	public double scoreIterationTimelimited(boolean positive, Result r) throws FileNotFoundException, IOException {
		ScoreIterationRunnable runnable = new ScoreIterationRunnable(this, positive, r);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future future = executor.submit(runnable);
		double result = 0;
		try {
			future.get(timeout_threshold, timeout_unit); 
			result = runnable.result;
			
		} catch (InterruptedException ie) { 
			  System.out.println("InterruptedException");
			  }
		catch (ExecutionException ee) { 
			  System.out.println("ExecutionException");		
			  }
		catch (TimeoutException te) { 
		  System.out.println("TimeoutException");
		}
		executor.shutdownNow();
		return result;
		
	}
	
	@Override
	public double scoreIteration(boolean positive, Result r) throws FileNotFoundException, IOException {
		
		// evolve the mine until suitable state
		Mine m = evolveMine(positive);
		  //System.out.println("Found A1 "+positive+" "+m.saveAsJson());
		
		// generate suitable RDF
		ExternalDB eDB = TestResultUtil.getDB();
		eDB.loadRDF(new StringReader(m.getSensorRDF()), RDFFormat.TURTLE);	
		// computing rule closure is expensive, so we only do it when necessary
		TestResultUtil.addVocabularyFiles(eDB);
		if(this.getClass() == TestResult_a4.class || this.getClass() == TestResult_a0.class || this.getClass() == TestResult_b4.class) {			
			PredicateEvaluation.computeRuleClosure(eDB, TestResultUtil.getRules(), TestResultUtil.getPredicates());
		}
		
		double score = -1;
		String query;
		if(isIfThen()) {
			// generate a SPARQL UPDATE query
			query = TestResultUtil.getSPARQLquery(r.getIfBlock());
		} else {
			query = TestResultUtil.getSPARQLquery(r.getUnsassigned());
		}		
		// generate SPARQL query
		// evaluate query
		try {
			TupleQueryResult result = eDB.query(query);
			score = evaluateQueryResults(m,result,r);
			result.close();				
		} catch (HTTPQueryEvaluationException e) {
			System.out.println("  ~~ Query not completable");
			score = 0;
			eDB.clearDB();
		}
		
		eDB.clearDB();
		return score;
	}
	
	public Mine evolveMine(boolean positive) throws FileNotFoundException, IOException {
		Mine m = TestResultUtil.getRandomMine();
		while(!conditionHolds(positive, m)) {
			for(int i = 0; i < 50; i++) {	
				for(int j = 0; j < 20; j++) {
					m.update(new HashSet<UserAction>());					
				}
				if(conditionHolds(positive, m)) {
					return m;
				}
			}
			m = TestResultUtil.getRandomMine();
		}
		return m;
		//throw new RuntimeException("Cannot generate mine");
	}
}
