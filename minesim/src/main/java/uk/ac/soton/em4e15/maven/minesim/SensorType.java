package uk.ac.soton.em4e15.maven.minesim;

public enum SensorType {
	TEMP, CO2, LOCATION, WORKERLOCATION;
	
	public String getCode() {
		switch(this) {
		case TEMP:
			return "Temperature";
		case CO2:
			return "CO2concentration";
		case LOCATION:
			return "Location";
		case WORKERLOCATION:
			return "WorkerLocation";
		}
		return null;
	}
}
