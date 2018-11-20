package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

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
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a4;

public abstract class TestResultPositive extends TestResultAbstract implements TestResultPositiveInterface{

	@Override
	public double score(Result r) throws FileNotFoundException, IOException {
		double totScore = 0;
		if(! r.solutionFound()) return 0;
		if(isIfThen() && (r.getIfBlock().size() == 0 || r.getThenBlock().size() == 0)) return 0;
		if(!isIfThen() && (r.getIfBlock().size() != 0 || r.getThenBlock().size() != 0)) return 0;
		// test cases when the predicate should trigger
		System.out.println(r);
		for(int i = 0; i  < TestResultUtil.getIterations(); i++) {
			totScore += scoreIteration(true, r);
			System.out.println("  POS "+totScore);
		}
		// test cases when the predicate should not trigger
		for(int i = 0; i  < TestResultUtil.getIterations(); i++) {
			totScore += scoreIteration(false, r);
			System.out.println("  POS+NEG "+totScore);
		}
		System.out.println("  TOT: ... ...  "+(totScore/(TestResultUtil.getIterations()*2) > 0.9 ? 1 : 0));
 		return totScore/(TestResultUtil.getIterations()*2) > 0.9 ? 1 : 0;
	}
	
	@Override
	public boolean isPositiveInstance() {
		return true;
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
		if(this.getClass() == TestResult_a4.class) {			
			PredicateEvaluation.computeRuleClosure(eDB, TestResultUtil.getRules(), TestResultUtil.getPredicates());
		}
		TestResultUtil.addVocabularyFiles(eDB);
		
		double score = -1;
		
		if(isIfThen()) {
			// generate a SPARQL UPDATE query
			
		} else {
			// generate SPARQL query
			String query = TestResultUtil.getSPARQLquery(r);
			// evaluate query
			try {
				TupleQueryResult result = eDB.query(query);
				score = evaluateQueryResults(m,result);
				result.close();				
			} catch (HTTPQueryEvaluationException e) {
				score = 0;
			}
		}		
		
		eDB.clearDB();
		return score;
	}
	
	public Mine evolveMine(boolean positive) throws FileNotFoundException, IOException {
		Mine m = TestResultUtil.getRandomMine();
		while(!conditionHolds(positive, m)) {
			for(int i = 0; i < 50; i++) {	
				for(int j = 0; j < 10; j++) {
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
