/**
 * @(#)database.java
 *
 *
 * @author 
 * @version 1.00 2025/11/26
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class database {
	
	public static HashMap<String, Object> rooms = new HashMap<>();
	public static HashMap<String, Guest> guestsDB = new HashMap<>();
	public static HashMap<String, Reserve> reservationData = new HashMap<>();
	public static HashMap<String, HashMap<String, Room>> roomsData = new HashMap<>();
	
	
	
	
	public static int GuestCount = 0;
	public static int totalReservations = 0;
	
	
    public database() {
    	
    }
    
    public static void addReservation (String guestId, String r_roomtype, int r_month, int r_day){
    	Reserve newReservation = new Reserve(guestId);
    	newReservation.RoomType = r_roomtype;
    	newReservation.month = r_month;
    	newReservation.day = r_day;
    	String reservationId = "R3S"+totalReservations;
    	reservationData.put(reservationId, newReservation);
    	totalReservations++;
    }
    
    public static void addGuest (String name, String email, String contact){
		String id = "G"+GuestCount;
		Guest newGuest = new Guest(name, email, contact);
		guestsDB.put(id, newGuest);
	}
	
	public static Guest getGuest (String Id){
		return guestsDB.get(Id);
	}
    
    public static void removeGuest (String Id){
    	guestsDB.remove(Id);
    }
}