import java.util.*;

public class Checkin {
	
	private Guest guest;
	public double nights;
	public String roomtype;
	public Room room;

    public Checkin(Guest newGuest) {
    	if (newGuest==null){
    		System.out.println("No guest found.");
    	} else {
    		this.guest = newGuest;
    		this.room = this.guest.getRoom();
    	}
    }
    
    public double getNights(){
    	return this.nights;
    }
    
    public String getRoomtype (){
    	return  this.roomtype;
    }
    
}