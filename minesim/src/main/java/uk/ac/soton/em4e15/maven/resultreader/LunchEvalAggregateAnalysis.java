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
import org.apache.commons.lang3.tuple.Pair;

public class LunchEvalAggregateAnalysis {

	private static EvaluationFile eval;
	
	public static DecimalFormat df = new DecimalFormat("#.###");
	
	public static String evaluationFile = "eval.csv";

	public static void main(String[] args) throws InterruptedException, IOException {
		
		eval = new EvaluationFile(evaluationFile);
		double totPrecision = 0;
		double totRecall = 0;
		for(String s : eval.getAllStns().stream().sorted(Comparator.comparing(n->n.toString())).collect(Collectors.toList())) {
			Pair<Double,Double> result = processStn(s);
			System.out.println("Score of   "+s+"   POS: "+df.format(result.getLeft())+" NEG: "+df.format(result.getRight()));
			totPrecision += result.getRight();
			totRecall += result.getLeft();
		}
		double numStns = eval.getAllStns().size();
		
		double averagePrecision = totPrecision/numStns;
		double averageRecall = totRecall/numStns;
		double averageF = 2*( (averagePrecision*averageRecall) / (averagePrecision + averageRecall) );
		System.out.println("TOTAL SCORE:\n >> Average Precision: "+averagePrecision+" Average recall: "+averageRecall);
		System.out.println(" >> Average F score: "+averageF);
	}
	
	public static Pair<Double,Double> processStn(String stn) throws IOException {
		double totNeg = 0;
		double totPos = 0;
		
		for(String s : eval.getasIDforStn(stn)) {
			Pair<Double,Double> result = processEntry(s);
			totNeg += result.getRight();
			totPos += result.getLeft();
		}
		double asIDnum = eval.getasIDforStn(stn).size();
		double truePositive = totPos;
		double falsePositive = asIDnum-totPos;
		double trueNegative = totNeg;
		double falseNegative = asIDnum-totNeg;
		double precision = truePositive / (truePositive+falsePositive);
		double recall = truePositive / (truePositive+falseNegative);
		double fScore = 2*( (precision*recall) / (precision + recall) );
		System.out.println("Task ["+stn+"]  TP("+truePositive+") TN("+trueNegative+") FP("+falsePositive+") FN("+falseNegative+") + "+totPos/(double)asIDnum+" + "+totNeg/(double)asIDnum+" ");
		System.out.println("  - F-measure = "+fScore);
		System.out.println("  - Precision = "+precision);
		System.out.println("  - Recall = "+recall); 
		return new ImmutablePair<Double,Double>(precision, recall);
	}
	
	public static Pair<Double,Double> processEntry(String asID) throws IOException {
		List<Double> pos = eval.getPos(asID);
		List<Double> neg = eval.getNeg(asID);
		return new ImmutablePair<Double,Double>(computeScore(pos),computeScore(neg));
	}
	
	private static Double computeScore(List<Double> list) {
		if(list.size() == 0) return new Double(0);
		double tot = 0;
		for(double d: list) {
			tot += d;
		}
		return new Double(tot/(double)list.size());
	}
}
