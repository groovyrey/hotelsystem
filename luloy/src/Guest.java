/**
 * @(#)Guest.java
 *
 *
 * @author 
 * @version 1.00 2025/11/28
 */


public class Guest {
	
	private String name = "";
	private String email = "";
	private String contact = "";
	private String id = "";
	
    public Guest(String guestName, String guestEmail, String guestContact) {
    	if (guestName.isEmpty() || guestEmail.isEmpty() || guestContact.isEmpty()){
    		System.out.print("Field Cannot be Empty.");
    	} else {
    		this.name = guestName;
    		this.email = guestEmail;
    		this.contact = guestContact;
    		this.id = "G"+database.GuestCount;
    		database.GuestCount++;
    	}
    }
    
    public String getId(){
    	return this.id;
    }
    
   public String getName(){
   	return this.name;
   }
   public String getEmail(){
   	return this.email;
   }
   public String getContact() {
   	return this.contact;
   }
    
}