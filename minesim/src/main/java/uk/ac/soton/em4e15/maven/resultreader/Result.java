package uk.ac.soton.em4e15.maven.resultreader;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;

public class Result {

	public String assignment_id;
	public String hit_id;
	public String worker_id;
	public String stn;
	public JsonObject result;
	
	public Result(String assignment_id, String hit_id, String worker_id, String stn, String result) {
		this.assignment_id = assignment_id;
		this.hit_id = hit_id;
		this.worker_id = worker_id;
		this.stn = stn;
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		try{
			this.result = jsonReader.readObject();
		} catch(JsonParsingException exc) {
			jsonReader = Json.createReader(new StringReader("{\"impossibleSnippet\":\""+(result.replaceAll("\"","\\\\\""))+"\"}"));
			this.result = jsonReader.readObject();
		}
	}
	
	@Override
	public String toString() {
		return "assignment_id: "+assignment_id+
				"\nhit_id: "+hit_id+
				"\nworker_id: "+worker_id+
				"\nstn: "+stn+
				"\n"+resultPrettyPrint(this)
				;
	}
	
	public String getPrettyResultPrint() {
		return resultPrettyPrint(this);
	}
	
	public boolean solutionFound() {
		return !result.containsKey("impossibleSnippet");
	}
	
	public String getImpossibilityReason() {
		return result.getString("impossibleSnippet");
	}
	
	public JsonArray getUnsassigned() {
		return result.getJsonArray("unassigned");
	}
	public JsonArray getIfBlock() {
		return result.getJsonArray("if_block");
	}
	public JsonArray getThenBlock() {
		return result.getJsonArray("then_block");
	}
	
	public static String resultPrettyPrint(Result result) {
		if (!result.solutionFound()) return "-- Impossible Because: "+result.getImpossibilityReason();
		JsonArray unassigned = result.getUnsassigned();
		JsonArray if_block = result.getIfBlock();
		JsonArray then_block = result.getThenBlock();
		String pp = "";
		for(JsonValue obj : unassigned) {
			pp += predicatePrettyPrint(obj)+" ";
		}
		if (if_block.size() > 0) pp += " <IF> ";
		for(JsonValue obj : if_block) {
			pp += predicatePrettyPrint(obj)+" ";
		}
		if (then_block.size() > 0) pp += " <THEN> ";
		for(JsonValue obj : then_block) {
			pp += predicatePrettyPrint(obj)+" ";
		}
		return pp;
	}
	public static String predicatePrettyPrint(JsonValue val) {
		String pp = val.asJsonObject().getString("name")+"(";
		for(JsonValue obj: val.asJsonObject().getJsonArray("variables"))
			pp += variablePrettyPrint(obj)+",";
		return pp+")";
	}
	public static String variablePrettyPrint(JsonValue obj) {
		String type = obj.asJsonObject().getString("type");
		if(type == "URI") return obj.asJsonObject().getString("val");
		if(type == "variable") return obj.asJsonObject().getString("val");
		return obj.asJsonObject().getString("val");
	}
}
