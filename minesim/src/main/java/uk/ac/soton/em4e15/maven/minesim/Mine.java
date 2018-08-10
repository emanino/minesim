package uk.ac.soton.em4e15.maven.minesim;
import java.io.StringReader;
//
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class Mine {
	
	private long layoutSeed;
	private long updateSeed;
	private Random layoutRand;
	private long updateRand;
	private long previousUpdates;
	private MineState state;
	private MineObjectScheduler scheduler;
	private Properties prop;

	private SortedSet<LayoutAtom> layoutAtomtoUpdate;
	
	public Mine(Properties prop, long layoutSeed, long updateSeed, long previousUpdates) {
		previousUpdates = 0;
		this.layoutSeed = layoutSeed;
		this.updateSeed = updateSeed;
		layoutRand = new Random(layoutSeed);
		updateRand = updateSeed;
		state = new MineState(0, prop);
		scheduler = new MineObjectScheduler(state, prop);
		this.prop = prop;
		layoutAtomtoUpdate = new TreeSet<LayoutAtom>(Comparator.comparing(MineObject::getId));
		createLayout();
		for(LayoutAtom las : layoutAtomtoUpdate) {
			las.initialiseLinks();
		}
		fillLayout();
		layoutAtomtoUpdate = null;
		state.activateCaching = true;
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
		for(MineObject obj: state.getObjectsSorted())
			obj.update(actions, scheduler, new Random(updateRand+previousUpdates), next);
		for(MineObject obj: next.getObjectsSorted())
			obj.postUpdate(actions, scheduler, new Random(updateRand+previousUpdates), next);
		// overwrite the old state with the new one
		previousUpdates++;
		state = next;
	}
	
	private void createLayout() {
		
		// read the layout parameters
		double atomRadius = Double.parseDouble(prop.getProperty("atomRadius"));
		int nSectionsMainTunnel = Integer.parseInt(prop.getProperty("nSectionsMainTunnel"));
		int nSectionsEscapeTunnel = Integer.parseInt(prop.getProperty("nSectionsEscapeTunnel"));
		int maxNSectionsSideTunnel = Integer.parseInt(prop.getProperty("maxNSectionsSideTunnel"));
		double mainToEscapeTunnelDistance = Double.parseDouble(prop.getProperty("mainToEscapeTunnelDistance"));
		int maxNRightSecondaryTunnels = Integer.parseInt(prop.getProperty("maxNRightSecondaryTunnels"));
		int maxNLeftSecondaryTunnels = Integer.parseInt(prop.getProperty("maxNLeftSecondaryTunnels"));
		double maxTunnelLength = Double.parseDouble(prop.getProperty("maxTunnelLength"));
		double southBoundary = Double.parseDouble(prop.getProperty("southBoundary"));
		
		// derive the corresponding number of atoms
		double atomDistance = atomRadius * 0.8; // slightly larger than 1/sqrt(2) so that diagonal atoms do not overlap 
		int nAtoms = (int) (maxTunnelLength / atomDistance) + 1;
		int nAtomsConnect = (int) (mainToEscapeTunnelDistance / atomDistance) + 1;
		
		// extract the position of the secondary tunnels on the right
		Set<Integer> rightSideJunctions = new HashSet<Integer>();
		while(maxNRightSecondaryTunnels > 0) {
			rightSideJunctions.add(layoutRand.nextInt(nSectionsMainTunnel));
			--maxNRightSecondaryTunnels;
		}
		
		// extract the position of the secondary tunnels on the left
		Set<Integer> leftSideJunctions = new HashSet<Integer>();
		while(maxNLeftSecondaryTunnels > 0) {
			leftSideJunctions.add(layoutRand.nextInt(nSectionsMainTunnel));
			--maxNLeftSecondaryTunnels;
		}
		
		// create the exits
		Position mainExit = new Position(0.0, 0.0, 0.0); // convention: the exit is always the origin
		Position emergencyExit = new Position(-mainToEscapeTunnelDistance, 0.0, 0.0); // convention: the escape tunnel is always on the left
		state.getExits().add(mainExit);
		state.getExits().add(emergencyExit);
		
		// create the main tunnel and the escape tunnel
		Position head = mainExit;
		Position head_bis = emergencyExit;
		for(int i = 0; i < nSectionsMainTunnel; ++i) {
			
			// main tunnel
			Position tail = head.plus(Direction.SOUTH.getVector().times(atomDistance * (double) nAtoms));
			if(tail.getY() > southBoundary) break; // hard boundary
			new Tunnel(state, head, tail, nAtoms, new LayoutAtomStatus(), atomRadius, layoutAtomtoUpdate);
			
			// escape tunnel and connection
			Position tail_bis = tail;
			if(i < nSectionsEscapeTunnel) {
				tail_bis = tail.plus(emergencyExit); // hack: emergencyExit is also the distance between the tunnels :-)
				new Tunnel(state, head_bis, tail_bis, nAtoms, new LayoutAtomStatus(), atomRadius, layoutAtomtoUpdate);
				new Tunnel(state, tail, tail_bis, nAtomsConnect, new LayoutAtomStatus(), atomRadius, layoutAtomtoUpdate);
			}
			
			// extract secondary tunnels on the right
			if(rightSideJunctions.contains(i))
				extendTunnel(tail, Direction.EAST, maxNSectionsSideTunnel, nAtoms - 2, atomRadius, 1);
			
			// extract secondary tunnels on the left
			if(leftSideJunctions.contains(i))
				extendTunnel(tail, Direction.WEST, maxNSectionsSideTunnel, nAtoms - 2, atomRadius, 1);
			
			// next
			head = tail;
			head_bis = tail_bis;
		}
	}
	
	private void fillLayout() {
		
		// all of these parameters should go into a configuration file
		int nMinerPeople = Integer.parseInt(prop.getProperty("nMinerPeople"));
		int nFirePeople = Integer.parseInt(prop.getProperty("nFirePeople"));
		
		// create all the miners close to the exit
		for(int i = 0; i < nMinerPeople; ++i) {
			Position pos = new Position(2.0 * layoutRand.nextDouble() - 1.0, 0.0, 0.0);
			MinerPerson miner = new MinerPerson(pos, state, new PersonStatus(prop));
			SimpleSensor portableSensor = new SimpleSensor(miner.getPosition(), state, SensorType.LOCATION);
			miner.addCarried(portableSensor);
			//miner.getStatus().setRestBar(Math.round(updateRand.nextDouble()*Double.parseDouble(prop.getProperty("personRestTime"))/10));
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
		
		// read boundaries
		double westBoundary = Double.parseDouble(prop.getProperty("westBoundary"));
		double eastBoundary = Double.parseDouble(prop.getProperty("eastBoundary"));
		double southBoundary = Double.parseDouble(prop.getProperty("southBoundary"));
		double northBoundary = Double.parseDouble(prop.getProperty("northBoundary"));
		
		// add a Tunnel section
		double atomDistance = atomRadius * 0.8; // slightly larger than 1/sqrt(2) so that diagonal atoms do not overlap 
		Position tail = head.plus(dir.getVector().times(atomDistance * (double) nAtoms));
		if(tail.getY() < northBoundary || tail.getY() > southBoundary || tail.getX() > westBoundary || tail.getX() < eastBoundary)
			return false; // make sure we stay underground and in the boundaries
		LayoutAtomStatus las = new LayoutAtomStatus();
		Tunnel t = new Tunnel(state, head, tail, nAtoms, las, atomRadius, layoutAtomtoUpdate);
		las.setSensorId(t.getId());
		
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
			continues |= extendTunnel(tail, directions.get(i), nSections - 1, nAtoms / 2 + 1, atomRadius, nDirChanges + 1);
		
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
			new SimpleSensor(atom.getPosition(), state, SensorType.TEMP);
			new SimpleSensor(atom.getPosition(), state, SensorType.CO2);
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
		return state.toJsonGui(scheduler);
	}
	
	public String getSensorRDF() {
		return state.getSensorRDF(scheduler);
	}
}
