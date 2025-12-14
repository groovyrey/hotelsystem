import java.util.UUID;

public class Guest {
	
	private String name = "";
	private String email = "";
	private String contact = "";
	private String id = "";
	private Room room;
	private int status = 0;
	
    public Guest(String guestName, String guestEmail, String guestContact) {
    	if (guestName.isEmpty() || guestEmail.isEmpty() || guestContact.isEmpty()){
    		System.out.print("Field Cannot be Empty.");
    	} else {
    		this.name = guestName;
    		this.email = guestEmail;
    		this.contact = guestContact;
    		this.id = guestEmail;
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
   
   public void setRoom (Room newRoom){
   	this.room = newRoom;
   }
   public void setStatus (int stat){
   	this.status = stat;
   }
   public int getStatus (){
   	return  this.status;
   }
   public Room getRoom (){
   	return this.room;
   }
    
}





