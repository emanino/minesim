package uk.ac.soton.em4e15.maven.resultreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class EvaluationFile {

	// expected columns
	// "asID","stn","pos","neg"
	// pos and neg are json formatted lists of doubles
	
	private String file;
	private Iterable<CSVRecord> records;
	
	public EvaluationFile(String filename) throws IOException {
		this.file = filename;
		
	}
	
	public boolean containsAsID(String asID) throws IOException {
		Reader in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
		    if(record.get("asID").equals(asID))
		    	in.close();
		    	return true;
		}
		in.close();
		return false;
	} 
	
	
	public Set<String> getAllStns() throws IOException{
		Reader in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		Set<String> stns = new HashSet<String>();
		for (CSVRecord record : records) {
			stns.add(record.get("stn"));
		}
		in.close();
		return stns;
	}
	
	public Set<String> getasIDforStn(String stn) throws IOException{
		Reader in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		Set<String> asIDs = new HashSet<String>();
		for (CSVRecord record : records) {
			if(record.get("stn").equals(stn)) {
				asIDs.add(record.get("asID"));
			}
		}
		in.close();
		return asIDs;
	}
	
	public List<Double> getPos(String asID) throws IOException{
		return getDoubles(asID, "pos");
	}
	public List<Double> getNeg(String asID) throws IOException{
		return getDoubles(asID, "neg");
	}
	private List<Double> getDoubles(String asID,String key) throws IOException{
		Reader in = new FileReader(file);
		records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		String results = null;
		for (CSVRecord record : records) {
		    if(record.get("asID").equals(asID))
		    	results = record.get(key);
		}
		if(results == null) {
			in.close();
			return null;
		}
		JsonReader jsonReader = Json.createReader(new StringReader(results));
		JsonArray jarr = jsonReader.readArray();
		List<Double> doublelist = new LinkedList<Double>();
		for(JsonNumber jn: jarr.getValuesAs(JsonNumber.class)) {
			doublelist.add(jn.doubleValue());
		}
		in.close();
		return doublelist;
	}
	
	public void addEntry(String asID, String stn, List<Double> pos, List<Double> neg) throws IOException {
		if(containsAsID(asID)) throw new RuntimeException("ERROR, tried to create two entries for the same assignment ID");
		Writer output = new BufferedWriter(new FileWriter(file, true));
		output.append("\""+asID+"\","+"\""+stn+"\","+"\""+pos+"\","+"\""+neg+"\"\n");
		output.close();
	}
	public void addEntry(String asID, String stn, String pos, String neg) throws IOException {
		if(containsAsID(asID)) throw new RuntimeException("ERROR, tried to create two entries for the same assignment ID");
		Writer output = new BufferedWriter(new FileWriter(file, true));
		output.append("\""+asID+"\","+"\""+stn+"\","+"\""+pos+"\","+"\""+neg+"\"\n");
		output.close();
	}
	
	public void removeEntry(String asID) throws IOException {
		File inputFile = new File(file);
		File tempFile = new File(file+"temp");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while((currentLine = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = currentLine.trim();
		    if(trimmedLine.contains("\""+asID+"\"")) continue;
		    writer.write(currentLine + System.getProperty("line.separator"));
		    System.out.println("Removed line "+asID);
		}
		writer.close(); 
		reader.close(); 
		tempFile.renameTo(inputFile);
		
	}
}
