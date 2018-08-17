package uk.ac.soton.em4e15.maven.minesim;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class MineStatisticsTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream("WebContent/WEB-INF/minesim.properties"));
		MineState state = new MineState(0, prop);
		
		MineStatistics stats = new MineStatistics(state);
		
		Position pos = new Position(2.0, 0.0, 0.0);
		SortedSet<LayoutAtom> layoutAtomToUpdate = new TreeSet<LayoutAtom>(Comparator.comparing(MineObject::getId));
		new Tunnel(state, new Position(0.0, 0.0, 0.0), pos, 3, new LayoutAtomStatus(prop, -1), 1.0, layoutAtomToUpdate, true);
		MinerPerson miner = new MinerPerson(pos, state, new PersonStatus(prop), stats);
		Fire fire = new Fire(pos, state, new FireStatus(prop), stats);
		
		stats.recordCO2RiskEvent(miner, state.getClosestLayoutAtom(pos));
		stats.recordFireStart(fire);
		stats.update(state); // hack: we should pass a new state
		stats.recordEndOfShift(miner);
		stats.recordFireEnds(fire);
		
		assertTrue("No main fires should have occurred", stats.getMainFiresLength().size() == 0);
		assertTrue("Just one side fire should have occurred", stats.getSideFiresLength().size() == 1);
		assertTrue("No deaths should have occurred", stats.getNumberOfDeaths() == 0);
		assertTrue("No production should have occurred", stats.getProduction() == 0.0);
		
		assertTrue("No instant temp risks should have occurred", stats.getTempRiskTracker().getInstantRisk() == 0.0);
		assertTrue("No shift temp risks should have occurred", stats.getTempRiskTracker().getShiftRisk() == 0.0);
		assertTrue("Just one instant co2 risk should have occurred", stats.getCO2RiskTracker().getInstantRisk() == 1.0);
		assertTrue("Just one shift co2 risk should have occurred", stats.getCO2RiskTracker().getShiftRisk() == 1.0);
	}
}
