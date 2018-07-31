package uk.ac.soton.em4e15.maven.minesim;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
		SortedSet<LayoutAtomStatus> orderedSet = new TreeSet<LayoutAtomStatus>(Comparator.comparing(LayoutAtomStatus::getSensorId));
		orderedSet.addAll(objects);
		return orderedSet;
	}
	
}
