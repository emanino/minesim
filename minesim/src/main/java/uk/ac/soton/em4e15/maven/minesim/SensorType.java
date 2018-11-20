package uk.ac.soton.em4e15.maven.minesim;

public enum SensorType {
	TEMP, CO, LOCATION, WORKERLOCATION;
	
	public String getCode() {
		switch(this) {
		case TEMP:
			return "Temperature";
		case CO:
			return "COconcentration";
		case LOCATION:
			return "Location";
		case WORKERLOCATION:
			return "WorkerLocation";
		}
		return null;
	}
}
