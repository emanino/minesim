package uk.ac.soton.em4e15.maven.resultreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class LunchEvalAggregateAnalysis {

	private static EvaluationFile eval;
	
	public static DecimalFormat df = new DecimalFormat("#.###");
	
	public static String evaluationFile = "eval.csv";
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		eval = new EvaluationFile(evaluationFile);
		double totPrecision = 0;
		double totRecall = 0;
		double totFmeasure = 0;
		double totRateCorrect = 0;
		for(String s : eval.getAllStns().stream().sorted(Comparator.comparing(n->n.toString())).collect(Collectors.toList())) {
			Triple<Double,Double,Double> result = processStn(s);
			System.out.println("Score of   "+s+"   POS: "+df.format(result.getLeft())+" NEG: "+df.format(result.getRight()));
			totRateCorrect += result.getRight();
			totPrecision += result.getLeft();
			totRecall += result.getMiddle();
			totFmeasure += result.getMiddle()+result.getLeft() == 0 ? 0 : 2*( (result.getLeft()*result.getMiddle()) / (result.getLeft() + result.getMiddle()) );
		}
		double numStns = eval.getAllStns().size();
		
		double averagePrecision = totPrecision/numStns;
		double averageRecall = totRecall/numStns;
		double averageRateCorrect = totRateCorrect/numStns;
		double averageF = 2*( (averagePrecision*averageRecall) / (averagePrecision + averageRecall) );
		double averageF2 = totFmeasure/numStns;
		System.out.println("TOTAL SCORE:\n >> Average Precision: "+averagePrecision+" Average recall: "+averageRecall);
		System.out.println(" >> Average F score: "+averageF2);
		System.out.println(" >> F score of the average precision and recall: "+averageF);
		System.out.println(" >> Average correctness rate: "+averageRateCorrect);
	}
	
	public static Triple<Double,Double,Double> processStn(String stn) throws IOException {
		double totNeg = 0;
		double totPos = 0;
		
		double totCorrect = 0;
		double totWrong = 0;
		for(String s : eval.getasIDforStn(stn)) {
			Triple<Double,Double,Double> result = processEntry(s);
			if(result.getRight() >= 1) 
				totCorrect++;
			else
				totWrong++;
			totNeg += result.getMiddle();
			totPos += result.getLeft();
		}
		double asIDnum = eval.getasIDforStn(stn).size();
		double truePositive = totPos;
		double falsePositive = asIDnum-totPos;
		double trueNegative = totNeg;
		double falseNegative = asIDnum-totNeg;
		boolean nan = truePositive+falsePositive == 0;
		boolean nan1 = truePositive+falseNegative == 0;
		double precision = nan ? 0 : truePositive / (truePositive+falsePositive);
		double recall = nan1 ? 0 : truePositive / (truePositive+falseNegative);
		boolean nan2 = precision+recall == 0;
		double fScore = nan2 ? 0 : 2*( (precision*recall) / (precision + recall) );
		double rateCorrect = totCorrect / (totCorrect+totWrong);
		System.out.println("Task ["+stn+"]  TP("+truePositive+") TN("+trueNegative+") FP("+falsePositive+") FN("+falseNegative+") + "+totPos/(double)asIDnum+" + "+totNeg/(double)asIDnum+" ");
		System.out.println("  - Correct Rate = "+rateCorrect);
		System.out.println("  - F-measure = "+fScore);
		System.out.println("  - Precision = "+precision);
		System.out.println("  - Recall = "+recall); 
		return new ImmutableTriple<Double,Double,Double>(precision, recall,rateCorrect);
	}
	
	public static Triple<Double,Double,Double> processEntry(String asID) throws IOException {
		List<Double> pos = eval.getPos(asID);
		List<Double> neg = eval.getNeg(asID);
		Double correct = isCorrect(pos, neg) ? 1.0 : 0.0;
		return new ImmutableTriple<Double,Double,Double>(computeScore(pos),computeScore(neg),correct);
	}
	
	private static Double computeScore(List<Double> list) {
		if(list.size() == 0) return new Double(0);
		double tot = 0;
		for(double d: list) {
			tot += d;
		}
		return new Double(tot/(double)list.size());
	}
	
	/**
	 * A solution is correct if there is no error
	 * @param list1
	 * @param list2
	 * @return
	 */
	private static boolean isCorrect(List<Double> list1, List<Double> list2) {
		double errors = 0;
		for(Double d : list1) if (d < 1) errors++;
		for(Double d : list2) if (d < 1) errors++;
		if(errors > 0) return false;
		return true;
	}
}
