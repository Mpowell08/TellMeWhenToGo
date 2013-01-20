package fireminder.TellMeWhenToGo;

import GoogleMatrixAPIHelpers.GoogleDMResult;

public class AppointmentTravelInfo {
	private String origin_address;
	private String destination_address;
	private int duration_value;
	private long arrival_time;
	
	public AppointmentTravelInfo(GoogleDMResult result, long arrival_time){
		this.origin_address = result.getOrigin();
		this.destination_address = result.getDestination();
		this.duration_value = result.getDuration();
		this.arrival_time = arrival_time;
	}
	
	public long getTravelTime(){
		return arrival_time - (long) (duration_value*1000);
	}
}
