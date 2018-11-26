package uk.ac.soton.em4e15.maven.resultreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;

import logic.Binding;
import logic.BindingImpl;
import logic.ExternalDB;
import logic.ExternalDB_GraphDB;
import logic.FileParser;
import logic.LabelService;
import logic.LabelServiceImpl;
import logic.Predicate;
import logic.PredicateExpansion;
import logic.PredicateExpansionBySPARQLquery;
import logic.PredicateInstantiation;
import logic.PredicateInstantiationImpl;
import logic.PredicateUtil;
import logic.RDFUtil;
import logic.ResourceLiteral;
import logic.ResourceURI;
import logic.Rule;
import uk.ac.soton.em4e15.maven.minesim.EscapeTunnel;
import uk.ac.soton.em4e15.maven.minesim.LayoutAtom;
import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.MovingObject;
import uk.ac.soton.em4e15.maven.minesim.Person;
import uk.ac.soton.em4e15.maven.minesim.SensorType;
import uk.ac.soton.em4e15.maven.minesim.SimpleSensor;
import uk.ac.soton.em4e15.maven.minesim.Tunnel;
import uk.ac.soton.em4e15.maven.minesim.observresult.ObservedDouble;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class TestResultUtil {

	public static ExternalDB getDB() throws IOException {
		return new ExternalDB_GraphDB("http://10.22.15.92:7200/", "test", "temp");
	}
	
	private static Set<Predicate> predicates;
	private static Set<Rule> rules;
	
	public static Set<Rule> getRules() throws IOException {
		if(rules == null) computePredicates();
		return rules;
	}
	
	public static Set<Predicate> getPredicates() throws IOException {
		if(predicates == null) computePredicates();
		return predicates;
	}
	
	public static String getSPARQLquery(JsonArray r) throws IOException {
		Set<String> query = new HashSet<String>();
		for(JsonValue obj : r) {
			query.add(formalisePredicate(obj));
		}
		String queryString = RDFUtil.getSPARQLdefaultPrefixes()+"\nSELECT * WHERE {\n";
		for(String q: query) {
			queryString += q+"\n";
		}
		return queryString+"}";
	}
	
	public static String formalisePredicate(JsonValue obj) throws IOException {
		Set<String> query = new HashSet<String>();
		Map<Integer,Binding> bindings = new HashMap<Integer,Binding>();
		String predicateName = obj.asJsonObject().getString("name");
		for(JsonValue var: obj.asJsonObject().getJsonArray("variables")){
			processBinding(bindings, var);
		}		
		Binding[] b = new Binding[bindings.size()];
		for(int i = 0; i < b.length; i++) {
			b[i] = bindings.get(i);
		}
		Predicate p = PredicateUtil.get(predicateName, bindings.size(), getPredicates());
		PredicateInstantiation pi = new PredicateInstantiationImpl(p, b);
		return pi.toSPARQL();
	}
	public static int stringToInt(String s) {
		String r = "";
		for(char c: s.toCharArray()) r += (int) c;
		return new Integer(r);
	}
	public static void processBinding(Map<Integer,Binding> bindings, JsonValue var) {
		String type = var.asJsonObject().getString("type");
		Integer varnum = new Integer(var.asJsonObject().getString("varnum"));
		if(type.equals("URI")) 
			bindings.put(varnum, new BindingImpl(new ResourceURI(var.asJsonObject().getString("val"))));
		else if(type.equals("http://www.w3.org/2001/XMLSchema#decimal")) 
			bindings.put(varnum, new BindingImpl(new ResourceLiteral(var.asJsonObject().getString("val"), type)));
		else if(type.equals("variable")) {
			bindings.put(varnum, new BindingImpl(stringToInt(var.asJsonObject().getString("val"))));
		} else {
			bindings.put(varnum, new BindingImpl(new ResourceLiteral(var.asJsonObject().getString("val"), type)));
		}
	}
	
	public static void addVocabularyFiles(ExternalDB eDB) throws RDFParseException, RepositoryException, IOException {
		String basePath = System.getProperty("user.dir");
		String[] vocabularyFiles = new String[] {
				basePath + "/resources/vocabularies/SSN.ttl",
				basePath + "/resources/vocabularies/rdf.ttl",
				basePath + "/resources/vocabularies/rdfs.ttl",
				basePath + "/resources/vocabularies/mine.ttl"
		};
		for(String filepath : vocabularyFiles) {
			eDB.loadRDF(new File(filepath), RDFFormat.TURTLE);
		}
	}
	
	
	public static void computePredicates() throws IOException {
		System.out.println("*************** COMPUTING RULE EXPANSION\n");
		String basePath = System.getProperty("user.dir");
		basePath = "/home/paolo/eclipse-workspace2/Simulator2D";
		String rulefile =  basePath+ "/resources/rulesBasic01.txt";
		String[] vocabularyFiles = new String[] {
				basePath + "/resources/vocabularies/SSN.ttl",
				basePath + "/resources/vocabularies/rdf.ttl",
				basePath + "/resources/vocabularies/rdfs.ttl"
		};
		ExternalDB eDB = new ExternalDB_GraphDB("http://10.22.15.92:7200/", "test", "temp");
		for(String filepath : vocabularyFiles) {
			eDB.loadRDF(new File(filepath), RDFFormat.TURTLE);
		}
		Map<String,String> prefixes = FileParser.parsePrefixes(basePath + "/resources/prefixes.txt");
		Set<PredicateInstantiation> existingPredicates = new HashSet<PredicateInstantiation>();
		Set<PredicateInstantiation> printPredicates = new HashSet<PredicateInstantiation>();
		
		predicates = new HashSet<Predicate>();
		rules = new HashSet<Rule>();
		FileParser.parse(rulefile, predicates, rules, existingPredicates, printPredicates, true, eDB);
		LabelService labelservice = new LabelServiceImpl(existingPredicates, prefixes);
		RDFUtil.labelService = labelservice;
		Model additionalVocabularies = ModelFactory.createDefaultModel();
		for(String filepath : vocabularyFiles) {
			Model externalVocabulary = RDFUtil.loadModel(filepath);
			RDFUtil.loadLabelsFromModel(externalVocabulary);
			additionalVocabularies.add(externalVocabulary);
		}
		RDFUtil.addToDefaultPrefixes(prefixes);
		RDFUtil.addToDefaultPrefixes(additionalVocabularies);
		for(String s: prefixes.keySet()) {
			eDB.setNamespace(s,prefixes.get(s));
		}
		PredicateExpansion expansion = new PredicateExpansionBySPARQLquery(predicates, rules, additionalVocabularies);
		expansion.setPrefixes(prefixes);
		Set<PredicateInstantiation> newPredicates = expansion.expand(existingPredicates);
		predicates = expansion.getPredicates();
		eDB.clearDB();
		System.out.println("*************** EXPANSION FINISHED\n");
	}
	
	/**
	 * How many scenarios to consider for every solution
	 * @return
	 */
	public static int getIterations() {
		return 5;
	}
	public static Random rand = new Random();
	
	public static Integer getRandomUpdateSeed() {
		return rand.nextInt();
	}
	public static Integer getRandomLayoutSeed() {
		return rand.nextInt();
	}
	public static Mine getRandomMine() throws FileNotFoundException, IOException {
		int mineSeed = getRandomLayoutSeed();
		int updateSeed = getRandomUpdateSeed();
		Properties prop = new Properties();
		prop.load(new FileReader("WebContent/WEB-INF/minesim.properties"));
		//prop.load(new FileInputStream(new File(resourceUrl.toString())));
		Mine mine = new Mine(prop, mineSeed, updateSeed, 0);
		// add a small amount of update actions, so that we do not consider mines with no history
		for(int i = 0; i < 1+Math.random()*20; i++) {
			mine.update(new HashSet<UserAction>());
		}
		return mine;
	}
	
	public static Set<String> getMainTunnels(Mine m){
		Set<String> mainTunnels = new HashSet<String>();
		for(EscapeTunnel s: m.getState().getObjects(EscapeTunnel.class)) {
			mainTunnels.add(s.getURI());
		}
		return mainTunnels;
	}
	public static Set<String> getTunnelsAbove(Mine m, SensorType t, double treshold){
		Set<String> highT = new HashSet<String>();
		for(SimpleSensor s: m.getState().getObjects(SimpleSensor.class)) {
			if(s.getType().equals(t) && ((ObservedDouble)s.getReading()).getValue() > treshold) {
				highT.add(new String(s.getFeatureOfInterest()));
			}
		}
		return highT;
	}
	public static Set<String> getTunnelsWithHumans(Mine m){	
		Set<String> tunnelWithHumans = new HashSet<String>();
		for(Person s: m.getState().getObjects(Person.class)) {
			LayoutAtom currAtom = m.getState().getClosestLayoutAtom(s.getPosition());
			Tunnel t = m.getState().getObject(Tunnel.class, currAtom.getSuperId());
			tunnelWithHumans.add(t.getURI());
		}
		return tunnelWithHumans;
	}
	
}
