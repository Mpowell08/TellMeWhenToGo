package GoogleMatrixAPIHelpers;

import android.location.Location;

public class GoogleDMQuery {

	private boolean UsingLatLongCoords;
	private double latitude;
	private double longitude;
	private String destination;
	private String origin_address;

	public String MakeURL(){
		if(UsingLatLongCoords){
			String URL = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=";
			URL += latitude + "," + longitude + "&destinations=";
			URL += destination + "&mode=driving&language=en-US&sensor=false";
			return URL;
		} else {
			String URL = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=";
			URL += origin_address + "&destinations=";
			URL += destination + "&mode=driving&language=en-US&sensor=false";
			return URL;
		}
	}
	
	public GoogleDMQuery(Location currentLocation, String destination){
		this.UsingLatLongCoords = true;
		this.latitude = currentLocation.getLatitude();
		this.longitude = currentLocation.getLongitude();
		this.destination = destination.replace(" ", "+");
	}
	public GoogleDMQuery(double latitude, double longitude, String destination){
		this.UsingLatLongCoords = true;
		this.latitude = latitude;
		this.longitude = longitude;
		this.destination = destination.replace(" ", "+");
	}
	
	public GoogleDMQuery(String origin_address, String destination){
		this.UsingLatLongCoords = false;
		this.origin_address = origin_address.replace(" ", "+");
		this.destination = destination.replace(" ", "+");
	}
}
