public class Room {
	
	public String Type = "";
	public String Status = "";
	public String Id = "";
	public Guest guest = null;
	
	
    public Room(String roomType, String roomStatus) {
    	this.Type = roomType;
    	this.Status = roomStatus;
    }
    
    
}