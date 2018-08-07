package uk.ac.soton.em4e15.maven.minesim.observresult;

public class ObservedDouble implements ObservationValue{

	private Double result;

	public ObservedDouble(double d) {
		result = new Double(d);
	}
	public ObservedDouble(Double d) {
		result = new Double(d);
	}
	
	@Override
	public Double getValue() {
		return result;
	}
	
	@Override
	public String toJsonGui() {
		return "\""+result.toString()+"\"";
	}
	
}
