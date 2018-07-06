package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MineState {
	
	private Map<Integer, MineObject> objects;
	private int maxId;
	private Properties prop;
	
	MineState(int reserveIds, Properties prop) {
		objects = new HashMap<Integer, MineObject>();
		maxId = reserveIds;
		this.prop = prop;
	}
	
	MineState(MineState old) {
		this(old.getNextId(), old.getProp());
	}
	
	// use this if the object existed in a previous state already
	public void addOld(MineObject obj) {
		if(obj.getId() >= maxId)
			throw new IllegalArgumentException("Old MineObjects must keep their old id");
		objects.put(obj.getId(), obj);
	}
	
	// use this for brand new objects
	public void addNew(MineObject obj) {
		if(obj.getId() != maxId)
			throw new IllegalArgumentException("Brand new MineObjects must have id equals to getNextId()");
		objects.put(obj.getId(), obj);
		++maxId;
	}
	
	public void removeObject(Integer id) {
		objects.remove(id);
	}
	
	// return all the objects
	public Set<MineObject> getObjects() {
		return new HashSet<MineObject>(objects.values());
	}
	
	// return all the objects of the specified class
	public <T extends MineObject> Set<T> getObjects(Class<T> cls) {
		Set<T> subset = new HashSet<T>();
		for(MineObject obj: objects.values())
			if(cls.isInstance(obj))
				subset.add(cls.cast(obj));
		return subset;
	}
	
	// return a specific object
	public MineObject getObject(Integer id) {
		return objects.get(id);
	}
	
	// same as previous but with fancy type check and casting
	public <T extends MineObject> T getObject(Class<T> cls, Integer id) {
		MineObject obj = objects.get(id);
		if(cls.isInstance(obj))
			return cls.cast(obj);
		else
			throw new IllegalArgumentException("The object with this id has class " + obj.getClass().getSimpleName() +
												" instead of "+ cls.getSimpleName());
	}
	
	// return all objects of the specified class in the specified radius
	public <T extends AtomObject> Set<T> getObjectsInRadius(Class<T> cls, Position pos, double radius) {
		Set<T> subset = new HashSet<T>();
		for(MineObject obj: objects.values())
			if(cls.isInstance(obj) && ((AtomObject) obj).getPosition().distanceTo(pos) <= radius)
				subset.add(cls.cast(obj));
		return subset;
	}
	
	public LayoutAtom getClosestLayoutAtom(Position pos) {
		LayoutAtom atom = null;
		double minDist = Double.MAX_VALUE;
		for(MineObject obj: objects.values())
			if(obj instanceof LayoutAtom) {
				double distance = ((LayoutAtom) obj).getPosition().distanceTo(pos);
				if(distance < minDist) {
					minDist = distance;
					atom = (LayoutAtom) obj;
				}
			}
		return atom;
	}
	
	public int getNextId() {
		return maxId;
	}
	
	public Properties getProp() {
		return prop;
	}
	
	public String toJsonGui() {
		List<String> strings = new ArrayList<String>();
		for(Map.Entry<Integer, MineObject> entry: objects.entrySet())
			strings.add(entry.getValue().toJsonGui());
		return "{\"mineObjects\":[" + String.join(",", strings) + "]}";
	}
}
