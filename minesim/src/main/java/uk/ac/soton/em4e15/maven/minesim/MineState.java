package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MineState {
	
	private Map<Integer, MineObject> objects;
	private int maxId;
	
	MineState(int reserveIds) {
		objects = new HashMap<Integer, MineObject>();
		maxId = reserveIds;
	}
	
	public void addOld(MineObject obj) {
		if(obj.getId() >= maxId)
			throw new IllegalArgumentException("Old MineObjects must keep their old id");
		objects.put(obj.getId(), obj);
	}
	
	public void addNew(MineObject obj) {
		if(obj.getId() != maxId)
			throw new IllegalArgumentException("Brand new MineObjects must have id equals to getNextId()");
		objects.put(obj.getId(), obj);
		++maxId;
	}
	
	public void removeObject(Integer id) {
		objects.remove(id);
	}
	
	public MineObject getObject(Integer id) {
		return objects.get(id);
	}
	
	public Set<MineObject> getObjects() {
		return new HashSet<MineObject>(objects.values());
	}
	
	public Set<AtomObject> getObjectsInRadius(Position pos, double radius) {
		// vanilla implementation; think about a quad-tree instead
		Set<AtomObject> subset = new HashSet<AtomObject>();
		for(MineObject obj: objects.values())
			if(obj instanceof AtomObject && pos.distanceTo(((AtomObject) obj).getPosition()) <= radius)
				subset.add((AtomObject) obj);
		return subset;
	}
	
	public LayoutAtom getClosestLayoutAtom(Position pos) {
		// vanilla implementation; think about a quad-tree instead
		LayoutAtom atom = null;
		double minDist = Double.MAX_VALUE;
		for(MineObject obj: objects.values())
			if(obj instanceof LayoutAtom) {
				double distance = pos.distanceTo(((LayoutAtom) obj).getPosition());
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
}
