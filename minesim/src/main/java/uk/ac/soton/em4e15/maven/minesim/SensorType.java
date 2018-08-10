package uk.ac.soton.em4e15.maven.minesim;

public enum SensorType {
	TEMP, CO2, LOCATION, WORKERLOCATION;
	
	public String getCode() {
		switch(this) {
		case TEMP:
			return "TEMP";
		case CO2:
			return "CO2";
		case LOCATION:
			return "LOCATION";
		case WORKERLOCATION:
			return "WORKERLOCATION";
		}
		return null;
	}
}
