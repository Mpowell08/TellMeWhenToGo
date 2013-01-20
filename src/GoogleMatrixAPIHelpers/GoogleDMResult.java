package GoogleMatrixAPIHelpers;

public class GoogleDMResult {
	private String status;
	private String origin_address;
	private String destination_address;
	private int duration_value;
	
	public GoogleDMResult(String status, String origin,
			String destination, int duration){
		this.status = status;
		this.origin_address = origin;
		this.destination_address = destination;
		this.duration_value = duration;
	}
	
	public String getStatus(){
		return this.status;
	}
	public String getOrigin(){
		return this.origin_address;
	}
	public String getDestination(){
		return this.destination_address;
	}
	public int getDuration(){
		return this.duration_value;
	}
	
}
