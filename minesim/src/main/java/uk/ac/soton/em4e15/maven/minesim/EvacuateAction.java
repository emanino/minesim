package uk.ac.soton.em4e15.maven.minesim;

import java.util.Set;

public class EvacuateAction implements Action {
	
	private Set<Integer> recipients;
	private Set<Integer> atoms;
	
	EvacuateAction(Set<Integer> recipients, Set<Integer> atoms) {
		this.recipients = recipients;
		this.atoms = atoms;
	}

	@Override
	public Set<Integer> getRecipientIds() {
		return recipients;
	}

	public Set<Integer> getAtomIds() {
		return atoms;
	}
}
