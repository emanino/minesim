package uk.ac.soton.em4e15.maven.resultreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a1;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a10;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a2;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a3;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a4;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a5;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a6;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a7;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a8;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a9;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b10;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b6;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b7;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b8;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_b9;

public class ScoreSet {

	public Map<String, List<Double>> negativeScores;
	public Map<String, List<Double>> positiveScores;
	
	public Map<String, Integer> negativeTotalAnswers;
	public Map<String, Integer> positiveTotalAnswers;
	
	public Map<String, Integer> negativeCorrectAnswers;
	public Map<String, Integer> positiveCorrectAnswers;
	
	DecimalFormat df = new DecimalFormat("#.####");
	
	// enable debug prints
	public boolean dp = true;
	
	public ScoreSet() {
		df.setRoundingMode(RoundingMode.CEILING);
		
		negativeScores =  new HashMap<String, List<Double>>();
		positiveScores =  new HashMap<String, List<Double>>();
		negativeTotalAnswers = new HashMap<String, Integer>();
		positiveTotalAnswers = new HashMap<String, Integer>();
		negativeCorrectAnswers = new HashMap<String, Integer>();
		positiveCorrectAnswers = new HashMap<String, Integer>();
	}
	
	public void score(Map<String,Set<Result>> solutions) throws FileNotFoundException, IOException {
		for(String s: solutions.keySet()) {
			score(s, solutions.get(s));
		}
		System.out.println();
	}
	public void score(String label, Set<Result> results) throws FileNotFoundException, IOException {
		TestResult tr;
		if(label.equals("b10")) {
			tr = new TestResult_b10();
		} else if(label.equals("b9")) {
			tr = new TestResult_b9();
		} else if(label.equals("b8")) {
			tr = new TestResult_b8();
		} else if(label.equals("b7")) {
			tr = new TestResult_b7();
		} else if(label.equals("b6")) {
			tr = new TestResult_b6();
		} else if(label.equals("a10")) {
			tr = new TestResult_a10();
		} else if(label.equals("a9")) {
			tr = new TestResult_a9();
		} else if(label.equals("a8")) {
			tr = new TestResult_a8();
		} else if(label.equals("a7")) {
			tr = new TestResult_a7();
		} else if(label.equals("a6")) {
			tr = new TestResult_a6();
		} else if(label.equals("a1")) {
			tr = new TestResult_a1();
		} else if(label.equals("a2")) {
			tr = new TestResult_a2();
		} else if(label.equals("a3")) {
			tr = new TestResult_a3();
		} else if(label.equals("a4")) {
			tr = new TestResult_a4();
		} else if(label.equals("a5")) {
			tr = new TestResult_a5();
		} else {
			tr = null;
		}
		
		if(tr != null) {
			if(dp) System.out.println("SCORING HIT "+label);	
			for(Result r: results)
				score(label, tr, r);
		} else {
			if(dp) System.out.println("DOES NOT HAVE AUTOMATIC TESTS FOR "+label);			
		}
	}
	
	public void score(String label, TestResult tr, Result r) throws FileNotFoundException, IOException {
		Double score = tr.score(r);
		Map<String, List<Double>> relevantScores = positiveScores;
		Map<String, Integer> relevantTotalAnswers = positiveTotalAnswers;
		Map<String, Integer> relevantCorrectAnswers = positiveCorrectAnswers;
		if(!tr.isPositiveInstance()) {
			relevantScores = negativeScores;
			relevantTotalAnswers = negativeTotalAnswers;
			relevantCorrectAnswers = negativeCorrectAnswers;
		}
		if(!relevantScores.containsKey(label)) {
			relevantScores.put(label, new LinkedList<Double>());
		}
		if(!relevantTotalAnswers.containsKey(label)) {
			relevantTotalAnswers.put(label, new Integer(1));
		} else {
			relevantTotalAnswers.put(label, new Integer(relevantTotalAnswers.get(label)+1));			
		}
		if(TestResult.isCorrect(score)) {			
			if(!relevantCorrectAnswers.containsKey(label)) {
				relevantCorrectAnswers.put(label, new Integer(1));
			} else {
				relevantCorrectAnswers.put(label, new Integer(relevantCorrectAnswers.get(label)+1));	
			}
		}
		relevantScores.get(label).add(score);
	}
	public Double getTotScore(Collection<Double> scores) {
		double totscores = 0;
		for(Double s: scores) {
			totscores += s;
		}
		return totscores;
	}
	public int getTotCorrect(Collection<Double> scores) {
		int tot = 0;
		for(Double s: scores) {
			if(TestResult.isCorrect(s))
				tot += 1;
		}
		return tot;
	}
	public int getTotWrong(Collection<Double> scores) {
		int tot = 0;
		for(Double s: scores) {
			if(TestResult.isWrong(s))
				tot += 1;
		}
		return tot;
	}
	public int getTotSometimesCorrect(Collection<Double> scores) {
		int tot = 0;
		for(Double s: scores) {
			if(TestResult.isSometimesCorrect(s))
				tot += 1;
		}
		return tot;
	}
	public int getTotAnswers(Collection<Double> scores) {
		return scores.size();
	}
	public double getPrecision(Collection<Double> scores) {
		if(getTotAnswers(scores) == 0) return -1;
		return ((double)getTotCorrect(scores))/((double)getTotAnswers(scores));
	}
	/**
	 * ration of answers which are correct sometimes
	 * @param scores
	 * @return
	 */
	public double getTotSometimesCorrectPrecision(Collection<Double> scores) {
		if(getTotAnswers(scores) == 0) return -1;
		return ((double)getTotSometimesCorrect(scores))/((double)getTotAnswers(scores));
	}
	public double getAverageScore(Collection<Double> scores) {
		return (getTotScore(scores))/((double)getTotAnswers(scores));
	}
	
	public List<Double> flattenMapValues(Map<String, List<Double>> values){
		List<Double> tot = new LinkedList<Double>();
		for(List<Double> ld : values.values()) {
			tot.addAll(ld);
		}
		return tot;
	}
	
	public void prettyPrintScores() {
		System.out.println("\nPositive Scores: ");
		List<Double> flattenPositive = flattenMapValues(positiveScores);
		System.out.println("POSITIVE HITS: (Tot instances: "+getTotAnswers(flattenPositive)+") Avg Score: "+df.format(getAverageScore(flattenPositive)));
		System.out.println("-Precision: "+df.format(getPrecision(flattenPositive))+" Flexible Precision: "+df.format(getTotSometimesCorrectPrecision(flattenPositive)));
		for(String s: positiveScores.keySet()) {
			prettyPrintScores(s, positiveScores.get(s));
		}
		
		System.out.println("\nNegative Scores: ");
		List<Double> flattenNegative = flattenMapValues(negativeScores);
		System.out.println("NEGATIVE HITS: (Tot instances: "+getTotAnswers(flattenNegative)+") Avg Score: "+df.format(getAverageScore(flattenNegative)));
		System.out.println("-Precision: "+df.format(getPrecision(flattenNegative))+" Flexible Precision: "+df.format(getTotSometimesCorrectPrecision(flattenNegative)));
		for(String s: negativeScores.keySet()) {
			prettyPrintScores(s, negativeScores.get(s));
		}
		
		System.out.println("\nTotal Scores: ");
	}
	public void prettyPrintScores(String s, List<Double> scores) {
		/*int totCorrect = getTotCorrect(scores);
		int totWrong = getTotWrong(scores);
		int totSometimesCorrect = getTotSometimesCorrect(scores);
		int totAnswers = getTotAnswers(scores);
		double totscores = getTotScore(scores);*/
		System.out.println(" . . . "+s+": "+df.format(getAverageScore(scores))+" (avg score over "+getTotAnswers(scores)+" hits)");
		System.out.println(" . . .  - - - - Precision: "+df.format(getPrecision(scores))+" Flexible Precision: "+df.format(getTotSometimesCorrectPrecision(scores)));
	}
}
