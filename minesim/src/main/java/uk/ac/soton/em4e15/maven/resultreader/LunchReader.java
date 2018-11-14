package uk.ac.soton.em4e15.maven.resultreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LunchReader {

	private static String fieldEncloseCharacters = "@";
	private static String fieldSeparatorCharacters = "\\|";

	public static void main(String[] args) throws Exception {
		String resultFile = "res003.csv";
		
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
			line = reader.readLine();
			
		}
		reader.close();
		
		for(String stn : solutions.keySet()){
			System.out.println("STN "+stn);
			for(Result r : solutions.get(stn)) {
				System.out.println("> "+r.getPrettyResultPrint());
			}
			System.out.println("<<<<<");
		}
		ScoreSet score = new ScoreSet();
		score.score(solutions);
		score.prettyPrintScores();
	}
	
	public static String clean(String str) throws Exception {
		if(str.indexOf(fieldEncloseCharacters) != 0) throw new Exception("ERROR, malformed field");
		str = str.substring(1);
		if(! str.substring(str.length()-1, str.length()).equals(fieldEncloseCharacters)) throw new Exception("ERROR, malformed field");
		str = str.substring(0,str.length()-1);
		return str;
	}

}
