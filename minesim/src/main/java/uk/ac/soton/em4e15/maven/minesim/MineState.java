package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class MineState {
	
	private SortedMap<Integer, MineObject> objects;
	private int maxId;
	private Properties prop;
	// for optimisation purposes:
	private SortedSet<MineObject> sortedObjectsCache;
	private Map<Class, SortedSet> sortedObjectsByTypeCache = new HashMap<Class, SortedSet>();
	public boolean activateCaching = false;
	private SortedSet<Position> exits = new TreeSet<Position>(Comparator.comparing(Position::toJsonGui));
	
	MineState(int reserveIds, Properties prop) {
		objects = new TreeMap<Integer, MineObject>();
		maxId = reserveIds;
		this.prop = prop;
	}
	
	MineState(int reserveIds, Properties prop, SortedSet<Position> exits) {
		this(reserveIds, prop);
		this.exits = exits;
	}
	
	MineState(MineState old) {
		this(old.getNextId(), old.getProp(), old.getExits());
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
	public Collection<MineObject> getObjects() {
		return objects.values();
	}
	
	public SortedSet<MineObject> getObjectsSorted() {
		if(sortedObjectsCache == null || !activateCaching) {			
			sortedObjectsCache = MineUtil.getOrderedSet(objects.values());
		}
		return sortedObjectsCache;
	}
	
	// return all the objects of the specified class
	/*public <T extends MineObject> SortedSet<T> getObjects(Class<T> cls) {
		Set<T> subset = new HashSet<T>();
		for(MineObject obj: getObjects())
			if(cls.isInstance(obj))
				subset.add(cls.cast(obj));
		return MineUtil.getOrderedSet(subset);
	}*/
	public <T extends MineObject> SortedSet<T> getObjects(Class<T> cls) {
		if(!sortedObjectsByTypeCache.containsKey(cls)) {
			SortedSet<T> subset = new TreeSet<T>(Comparator.comparing(MineObject::getId));
			for(MineObject obj: getObjects())
				if(cls.isInstance(obj))
					subset.add(cls.cast(obj));
			
			sortedObjectsByTypeCache.put(cls, subset);
		}
		return (SortedSet<T>) sortedObjectsByTypeCache.get(cls);
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
	public <T extends AtomObject> SortedSet<T> getObjectsInRadius(Class<T> cls, Position pos, double radius) {
		SortedSet<T> subset = new TreeSet<T>(Comparator.comparing(MineObject::getId));
		for(MineObject obj: getObjects())
			if(cls.isInstance(obj) && ((AtomObject) obj).getPosition().distanceTo(pos) <= radius)
				subset.add(cls.cast(obj));
		return subset;
	}
	
	public LayoutAtom getClosestLayoutAtom(Position pos) {
		LayoutAtom atom = null;
		double minDist = Double.MAX_VALUE;
		for(MineObject obj: getObjects())
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
	
	public String toJsonGui(MineObjectScheduler scheduler) {
		List<String> strings = new ArrayList<String>();
		for(Map.Entry<Integer, MineObject> entry: objects.entrySet())
			strings.add(entry.getValue().toJsonGui());
		strings.addAll(scheduler.toJsonGui());		
		return "{\"mineObjects\":[" + String.join(",", strings) + "]}";
	}

	public SortedSet<Position> getExits() {
		return exits;
	}

	public void setExits(SortedSet<Position> exits) {
		this.exits = exits;
	}
}