/**
 * @(#)Reserve.java
 *
 *
 * @author 
 * @version 1.00 2025/12/1
 */


public class Reserve {
	
	public Guest reservedGuest;
	public String RoomType = "";
	public int month = 0;
	public int day = 0;

    public Reserve(String guestId) {
    	Guest checkGuest = database.getGuest(guestId);
    	
    	if (checkGuest == null){
    		System.out.println("Guest not found.");
    	} else {
    		System.out.println("Guest found: "+checkGuest.getName());
    		reservedGuest = checkGuest;
    	}
    }
    
    
}