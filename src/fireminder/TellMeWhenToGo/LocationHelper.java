package fireminder.TellMeWhenToGo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationHelper {
	LocationManager lm;
	Location current_location = null;
	
	public LocationHelper(Context c){
		lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
	}
	
	public Location getCurrentLocation(){
		while(current_location != null) {}
		return this.current_location;
	}
	private final LocationListener listener = new LocationListener(){

		@Override
		public void onLocationChanged(Location arg0) {
			current_location = arg0;
			Log.d("newLoc", current_location.toString());
			}

		@Override
		public void onProviderDisabled(String arg0) {
			}

		@Override
		public void onProviderEnabled(String arg0) {
			}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
		
	};
	
}
