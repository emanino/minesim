package uk.ac.soton.em4e15.maven.resultreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import GraphDB.EmbeddedGraphDB;

public class LunchReader {

	public static String fieldEncloseCharacters = "@";
	public static String fieldSeparatorCharacters = "\\|";

	public static void setLoggingLevel(ch.qos.logback.classic.Level level) {
	    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(level);
	}	
	
	
	// Configure input files
	static private String resultFile = "res003.csv";
	static private String evaluationFile = "eval.csv";

	/**
	 * This program requires GraphDB as an external database where to run queries with GeoSparql support.
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		boolean finished = false;
		while(!finished) {
			System.out.println("\n\n *** Evaluation start");
			printTime();
			try{
				finished = computeEvaluations();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e);
				e.printStackTrace();
				TimeUnit.SECONDS.sleep(5);
				System.out.println("EXCEPTION -- STOP");
				System.gc();
				TimeUnit.SECONDS.sleep(5);
				System.out.println("EXCEPTION -- RESTART");
			}
		}
		System.out.println("\n\n *** Evaluation complete");
		printTime();
	}
	
	private static void printTime() {
		System.out.println(new Date().toString());
	}
	
	public static boolean computeEvaluations() throws IOException  {
		setLoggingLevel(ch.qos.logback.classic.Level.ERROR);
		
		
		EvaluationFile eval = new EvaluationFile(evaluationFile);
		/*System.out.println("a "+eval.containsAsID("asdas"));
		System.out.println(" "+eval.containsAsID("test_as"));
		System.out.println(" "+eval.getPos("test_as"));
		List<Double> pos = eval.getPos("test_as");
		List<Double> neg = eval.getPos("test_as");
		eval.removeEntry("test_as");
		eval.addEntry("asdas","b12",pos, neg);
		System.out.println("a "+eval.containsAsID("asdas"));
		if(!eval.containsAsID("asdas")) throw new RuntimeException();
		if(eval.containsAsID("asdas")) throw new RuntimeException();*/
		
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(resultFile));
		String line = reader.readLine();
		
		Map<String,Set<Result>> solutions = new HashMap<String,Set<Result>>();
		
		while (line != null) {
			//System.out.println(line);
			// read next line
			String[] tokens = line.split(fieldSeparatorCharacters);
			Result r = new Result(clean(tokens[0]),
					clean(tokens[1]),
					clean(tokens[2]),
					clean(tokens[3]),
					clean(tokens[4]));
			if(!solutions.containsKey(r.stn))
				solutions.put(r.stn, new HashSet<Result>());
			solutions.get(r.stn).add(r);
			//System.out.println(r);
			
			// interpret the statements before the IF as part of the IF, as they effectively form a condition
			if(r.solutionFound() && r.getUnsassigned().size() > 0 && r.getIfBlock().size() > 0) {
				JsonArrayBuilder builder = Json.createArrayBuilder();
				for(JsonValue obj: r.getIfBlock()) {
					  builder.add(obj);
					}
				for(JsonValue obj: r.getUnsassigned()) {
					  builder.add(obj);
					}
				r.setIfBlock(builder.build());
				r.setUnsassigned(Json.createArrayBuilder().build());
			}
			line = reader.readLine();
			
		}
		reader.close();
		
		/*for(String stn : solutions.keySet()){
			System.out.println("STN "+stn);
			for(Result r : solutions.get(stn)) {
				System.out.println("> "+r.getPrettyResultPrint());
			}
			System.out.println("<<<<<");
		}*/
		
		TestResultUtil.computePredicates();
		printTime();
		
		ScoreSet score = new ScoreSet(eval);
		score.score(solutions);
		score.prettyPrintScores();
		EmbeddedGraphDB.closeAllOpenConnections();
		return true;
	}
	
	public static String clean(String str) throws RuntimeException {
		if(str.indexOf(fieldEncloseCharacters) != 0) throw new RuntimeException("ERROR, malformed field");
		str = str.substring(1);
		if(! str.substring(str.length()-1, str.length()).equals(fieldEncloseCharacters)) throw new RuntimeException("ERROR, malformed field");
		str = str.substring(0,str.length()-1);
		return str;
	}

}
