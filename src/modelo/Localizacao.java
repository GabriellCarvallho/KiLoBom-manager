package modelo;

public class Localizacao {
	
	private double latitude;
	private double longitude;
	
	public Localizacao(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
	
	@Override
	public String toString() {
		return "Localização: { Latitude: " + this.latitude + ", Longitude: " + this.longitude + "}";
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
