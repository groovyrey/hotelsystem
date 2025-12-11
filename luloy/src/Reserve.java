import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Reserve {
	
	public Guest reservedGuest;
	public String RoomType = "";
	public double nights;
	public Date date;

    public Reserve(String guestId) {
    	Guest checkGuest = database.getGuest(guestId);
    	
    	if (checkGuest == null){
    		System.out.println("Guest not found.");
    	} else {
    		
    		if (checkGuest.getStatus() == 0){
    			System.out.println("Guest found: "+checkGuest.getName());
    		} else {
    			System.out.println("Invalid guest status: "+checkGuest.getStatus());
    		}
    		reservedGuest = checkGuest;
    	}
    }
    
    
}