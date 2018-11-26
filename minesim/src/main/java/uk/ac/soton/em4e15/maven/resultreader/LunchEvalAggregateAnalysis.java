package uk.ac.soton.em4e15.maven.resultreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class LunchEvalAggregateAnalysis {

	private static EvaluationFile eval;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String evaluationFile = "eval.csv";
		eval = new EvaluationFile(evaluationFile);
		double totNeg = 0;
		double totPos = 0;
		for(String s : eval.getAllStns()) {
			Pair<Double,Double> result = processStn(s);
			System.out.println("Score of "+s+" POS: "+result.getLeft()+" NEG: "+result.getRight());
			totNeg += result.getRight();
			totPos += result.getLeft();
		}
		int numStns = eval.getAllStns().size();
		System.out.println("TOTAL SCORE:\n >> POS: "+(totPos/numStns)+" NEG: "+(totNeg/numStns));
		System.out.println("               >> Aggregate: "+(((totPos/numStns)+(totNeg/numStns))/2));
	}
	
	public static Pair<Double,Double> processStn(String stn) throws IOException {
		double totNeg = 0;
		double totPos = 0;
		
		for(String s : eval.getasIDforStn(stn)) {
			Pair<Double,Double> result = processEntry(s);
			totNeg += result.getRight();
			totPos += result.getLeft();
		}
		int asIDnum = eval.getasIDforStn(stn).size();
		return new ImmutablePair<Double,Double>(totPos/(double)asIDnum, totNeg/(double)asIDnum);
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
