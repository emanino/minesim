package uk.ac.soton.em4e15.maven.minesim;

import java.io.StringReader;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.PartialEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

import java.util.SortedSet;
import java.util.TreeSet;

public class MineUtil {

	
	public static <T extends MineObject> SortedSet<T> getOrderedSet(Collection<T> objects) {
		SortedSet<T> orderedSet = new TreeSet<T>(Comparator.comparing(MineObject::getId));
		orderedSet.addAll(objects);
		return orderedSet;
	}
	
	public static SortedSet<Integer> getOrderedSetInt(Collection<Integer> objects) {
		SortedSet<Integer> orderedSet = new TreeSet<Integer>();
		orderedSet.addAll(objects);
		return orderedSet;
	}
	
	// not sure if it works
	public static SortedSet<Entry<Integer, MineObject>> getOrderedSetIntObj(Collection<Entry<Integer, MineObject>> objects) {
		SortedSet<Entry<Integer, MineObject>> orderedSet = new TreeSet<Entry<Integer, MineObject>>(Comparator.comparing(Entry<Integer, MineObject>::getKey));
		orderedSet.addAll(objects);
		return orderedSet;
	}
	
	public static SortedSet<LayoutAtomStatus> getOrderedSetLayoutAtomStatus(Collection<LayoutAtomStatus> objects) {
		SortedSet<LayoutAtomStatus> orderedSet = new TreeSet<LayoutAtomStatus>(Comparator.comparing(LayoutAtomStatus::getAtomId));
		orderedSet.addAll(objects);
		return orderedSet;
	}
	
	public static Mine generateUpdatedMine(int mineSeed, int updateSeed, String jsonData, Properties prop) {
		JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
		JsonObject jobj = jsonReader.readObject();
		//prop.load(new FileInputStream(new File(resourceUrl.toString())));
		Mine mine = new Mine(prop, mineSeed, updateSeed, 0);
		
		int i = 1;
		JsonObject jobjcurrent = jobj.getJsonObject(""+i);
		while(jobjcurrent != null) {			
			int code = Integer.parseInt(jobjcurrent.getString("code"));
			int times = Integer.parseInt(jobjcurrent.getString("times"));
			String params = jobjcurrent.getString("params");
			for(int j = 0; j < times; j++) {
				if(code == 0) {
					// code 0 = Business as Usual
					mine.update(new HashSet<UserAction>());
				} else if (code == 1) {
					HashSet<UserAction> actions = new HashSet<UserAction>();
					actions.add(new FullEvacuateUserAction());
					mine.update(actions);
				} else if (code == 2) {
					Integer tunnelId = Integer.parseInt(params);
					HashSet<UserAction> actions = new HashSet<UserAction>();
					actions.add(new PartialEvacuateUserAction(tunnelId));
					mine.update(actions);
				}
			}
			i++;
			jobjcurrent = jobj.getJsonObject(""+i);
		}
		return mine;
	}
	
	public static Mine loadAsJson(String json, Properties prop) {
		JsonReader jsonReader = Json.createReader(new StringReader(json));
		JsonObject jobj = jsonReader.readObject();
		int layoutSeed = jobj.getInt("layoutSeed");
		int updateSeed = jobj.getInt("updateSeed");
		Mine mine = new Mine(prop, layoutSeed, updateSeed, 0);
		for(JsonValue val : jobj.getJsonArray("updateActions")) {
			JsonObject updateAction = val.asJsonObject();
			for(int i = 0; i < updateAction.getInt("number"); i++) {
				HashSet<UserAction> actions = new HashSet<UserAction>();
				JsonObject codes = (JsonObject) updateAction.getJsonArray("codes").get(0);
				int code = codes.getInt("code");
				if(code == 0) {
				} else if (code == 1) {
					actions.add(new FullEvacuateUserAction());
				} else if (code == 2) {
					int tunnel = Integer.parseInt(codes.getString("param"));
					actions.add(new PartialEvacuateUserAction(tunnel));
				} else throw new RuntimeException("ERROR: unrecognised action code ("+code+") when trying to load a mine.");
				mine.update(actions);
			}
		}
		return mine;
	}
	
	public static Random getRandom(long updateseed, long updates) {
		Random r = new Random(updateseed);
		for(int i = 0; i < updates; i++) {
			updateseed = updateseed + r.nextInt();
			r = new Random(updateseed);
			if(updates > 99999) updates = updates/10;
		}
		return r;
	}
}
