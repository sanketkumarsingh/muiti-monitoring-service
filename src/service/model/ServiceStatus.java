package service.model;

public enum ServiceStatus {

	UP("up"), DOWN("down"), OUTAGE_PERIOD("outage");
	
	private String value;
	
	private ServiceStatus(String value) {
		this.value = value;
	}

	public String toString() {
		return String.valueOf(this.value);
	}
}
