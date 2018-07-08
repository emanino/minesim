package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public class Mine {
	
	private long layoutSeed;
	private long updateSeed;
	private Random layoutRand;
	private Random updateRand;
	private MineState state;
	private MineObjectScheduler scheduler;
	private Properties prop;
	
	Mine(Properties prop, long layoutSeed, long updateSeed) {
		this.layoutSeed = layoutSeed;
		this.updateSeed = updateSeed;
		layoutRand = new Random(layoutSeed);
		updateRand = new Random(updateSeed);
		state = new MineState(0, prop);
		scheduler = new MineObjectScheduler(state, prop);
		this.prop = prop;
		
		createLayout();
		fillLayout();
	}

	// constructor from file
	// save minimal info on file
	
	public long getLayoutSeed() {
		return layoutSeed;
	}
	
	public long getUpdateSeed() {
		return updateSeed;
	}
	
	public MineState getState() {
		return state;
	}
	
	public void update(Set<UserAction> actions) {
		
		// new state and new action scheduler
		MineState next = new MineState(state);
		scheduler.update(actions, next);
		
		// update all the elements
		for(MineObject obj: state.getObjects())
			obj.update(scheduler, updateRand, next);
		
		// overwrite the old state with the new one
		state = next;
	}
	
	private void createLayout() {
		
		// all of these parameters should go into a configuration file
		int maxSections = 5;
		int maxAtoms = 10;
		double atomRadius = 1.0;
		
		Position exit = new Position(0.0, 0.0, 0.0); // convention: the exit is always the origin
		extendTunnel(exit, Direction.SOUTH, maxSections, maxAtoms, atomRadius, 0);
	}
	
	private void fillLayout() {
		
		// all of these parameters should go into a configuration file
		int nMinerPeople = 4;
		int nFirePeople = 1;
		
		// create all the miners close to the exit
		for(int i = 0; i < nMinerPeople; ++i) {
			Position pos = new Position(2.0 * layoutRand.nextDouble() - 1.0, 0.0, 0.0);
			new MinerPerson(pos, state, new PersonStatus(prop));
		}
		
		// create all the firemen close to the exit
		for(int i = 0; i < nFirePeople; ++i) {
			Position pos = new Position(2.0 * layoutRand.nextDouble() - 1.0, 0.0, 0.0);
			new FirePerson(pos, state, new PersonStatus(prop), new FireExtinguishingSkill());
		}
		
		// create the sensors
		LayoutAtom exit = state.getClosestLayoutAtom(new Position(0.0, 0.0, 0.0));
		placeSensors(exit, new HashSet<Integer>(), 1.0);
	}
	
	// HTML axis orientation (y is upside down)
	private enum Direction {
		
		NORTH(new Position(0, -1, 0)),
		SOUTH(new Position(0, +1, 0)),
		EAST(new Position(+1, 0, 0)),
		WEST(new Position(-1, 0, 0));
		
		private Position vector;
		
		private Direction(Position vector) {
			this.vector = vector;
		}
		
		public Position getVector() {
			return vector;
		}
		
		public boolean isOppositeTo(Direction dir) {
			switch(this) {
				case NORTH:
					return (dir == SOUTH);
				case SOUTH:
					return (dir == NORTH);
				case EAST:
					return (dir == WEST);
				case WEST:
					return (dir == EAST);
				default:
					return false; // just to please the compiler
			}
		}
	}
	
	private boolean extendTunnel(Position head, Direction dir, int nSections, int nAtoms, double atomRadius, int nDirChanges) {
		
		// end the recursion
		if(nSections <= 0 || nAtoms <= 0)
			return false;
		
		// add a Tunnel section
		double atomDistance = atomRadius * 0.8; // slightly larger than 1/sqrt(2) so that diagonal atoms do not overlap 
		Position tail = head.plus(dir.getVector().times(atomDistance * (double) nAtoms));
		if(tail.getY() <= 0.0)
			return false; // make sure we stay underground
		new Tunnel(state, head, tail, nAtoms, new LayoutAtomStatus(), atomRadius);
		
		// keep extending in the same direction with {100%, 70%, 40%, 10%} chance
		boolean continues = false;
		if(layoutRand.nextDouble() < 1.0 - (double) nDirChanges * 0.30)
			continues |= extendTunnel(tail, dir, nSections - 1, nAtoms, atomRadius, nDirChanges);
		
		// extract the branching factor in [-1,2] so that there is 50% chance of not branching
		int nBranches = layoutRand.nextInt(4) - 1;
		
		// extract the next directions
		List<Direction> directions = new ArrayList<Direction>();
		for(int i = 0; i < nBranches; ++i) {
			Direction nextDir = Direction.values()[layoutRand.nextInt(4)];
			if(nextDir == dir || nextDir.isOppositeTo(dir))
				--i;
			else
				directions.add(nextDir);
		}
		
		// branch!
		for(int i = 0; i < nBranches; ++i)
			continues |= extendTunnel(tail, directions.get(i), nSections - 1, nAtoms - 2, atomRadius, nDirChanges + 1);
		
		// add mining sites to the end of terminal tunnels
		if(!continues)
			new MiningSite(tail, state);
		
		return true;
	}
	
	private void placeSensors(LayoutAtom atom, Set<Integer> visited, double prob) {
		
		// end the recursion
		if(visited.contains(atom.getId()))
			return;
		
		// create a new sensor here
		if(layoutRand.nextDouble() >= prob) {
			new SimpleSensor(atom.getPosition(), state, SimpleSensor.SensorType.TEMP);
			new SimpleSensor(atom.getPosition(), state, SimpleSensor.SensorType.CO2);
			prob = 1.0;
		
			// or decrease the probability of not creating one next
		} else
			prob *= 0.98;
		
		// recursive visit of the whole graph
		visited.add(atom.getId());
		for(Integer atomId: atom.getNeighbours())
			placeSensors(state.getObject(LayoutAtom.class, atomId), visited, prob);
	}
	
	public String toJsonGui() {
		return state.toJsonGui();
	}
}
