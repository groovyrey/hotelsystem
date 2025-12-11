
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.*;
import java.text.SimpleDateFormat;

public class database {
	
	public static HashMap<String, Object> rooms = new HashMap<>();
	public static LinkedHashMap<String, Guest> guestsDB = new LinkedHashMap<>();
	public static LinkedHashMap<String, Reserve> reservationData = new LinkedHashMap<>();
	public static LinkedHashMap<String, LinkedHashMap<String, Room>> roomsData = new LinkedHashMap<>();
	public static HashMap<String, String> RoomTypes = new HashMap<>();
	public static HashMap<String, String> RoomStatus = new HashMap<>();
	public static HashMap<String, Double> RoomtypeRates = new HashMap<>();
	public static LinkedHashMap<String, Checkin> checkins = new LinkedHashMap<>();
	public static ArrayList<Log> logsDB = new ArrayList<>();
	
	private static boolean openwindow = false;
	
	public static JFrame currentFrame = new JFrame();
	
	public static int GuestCount = 0;
	public static int totalReservations = 0;
	private static int checkinCounts = 0;
	
    public database() {
    	
    }
    
    public static void addLog(String msg){
    	LocalTime logTime = LocalTime.now();
    	if (msg.isEmpty()){
    		System.out.println("No log message provided.");
    	} else {
    		Log newlog = new Log();
    		newlog.logtime = logTime;
    		newlog.message = msg;
    		logsDB.add(newlog);
    		
    	}
    }
    
    public static boolean newCheckin (Guest guest, double nights, String roomtype, Room targetRoom){
    	if (guest!=null){
    		if (guest.getRoom()==null){
    			if (guest.getStatus() == 0 || guest.getStatus() == 2){
    				Checkin newData = new Checkin(guest);
    				newData.room = targetRoom;
    				newData.nights = nights;
    				newData.roomtype = roomtype;
    				newData.room.Status = "O";
    				guest.setRoom(targetRoom);
    				guest.setStatus(1);
    				checkins.put(guest.getId(), newData);
    				targetRoom.guest = guest;
    				System.out.println("New checkin added. AssignedRoom: "+guest.getRoom());
    				String logmsg = "New Check-in: \n"+guest.getEmail();
    				database.addLog(logmsg);
    				return true;
    			} else {
    				JOptionPane.showMessageDialog(null, "Invalid guest status, guest is reserved or already checkedin", "Error", JOptionPane.ERROR_MESSAGE);
    				System.out.println("Invalid guest status.");
    				return false;
    			}
    			
    		} else {
    			JOptionPane.showMessageDialog(null, "Guest already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
    			return  false;
    		}
    		
    	} else {
    		System.out.println("Guest not found.");
    		return false;
    	}
    }
    
    public static boolean checkOut (String guestid, Checkin data){
    	Guest target = guestsDB.get(guestid);
    	
    	if (data!=null){
    		addLog("Guest: "+target.getId()+" Checked-out");
    		data.room.Status = "V";
    		data.room.guest = null;
    		
    		guestsDB.remove(guestid);
    		checkins.remove(guestid);
    		luloy.navigate("Rooms");
    	}
    	
    	return true;
    }
    
    public static boolean addReservation (String guestId, String r_roomtype, double nights, Date reservationDate){
    	Guest target = guestsDB.get(guestId);
    	
    	if (target!=null) {
    		Reserve newReservation = new Reserve(guestId);
    		newReservation.RoomType = r_roomtype;
    		newReservation.date = reservationDate;
    		newReservation.nights = nights;
    		if (target.getStatus() == 0){
    			String reservationId = "R3S"+totalReservations;
    			reservationData.put(reservationId, newReservation);
    			totalReservations++;
    			target.setStatus(2);
    			addLog("Reserved: "+target.getEmail());
    			
    			return true;
    		} else {
    			JOptionPane.showMessageDialog(null, "Invalid guest status, guest is reserved or already checkedin", "Error", JOptionPane.ERROR_MESSAGE);
    			System.out.println("Invalid guest status.");
    			return false;
    		}
    		
    		
    	} else {
    		return false;
    	}	
    }
    
    public static boolean removeReservation (String reservationId){
    	Reserve data = database.reservationData.get(reservationId);
    	
    	if (data!=null){
    		Guest target = data.reservedGuest;
    		target.setStatus(0);
    		reservationData.remove(reservationId);
    		addLog("Removed Reservation: "+target.getName());
    		return true;
    	} else {
    		System.out.println("Reservation data not found.");
    		return false;
    	}
    }
    
    public static Date getDate (int month, int day){
    	Date date = new Date(); 
		LocalDate reservationDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		reservationDate = reservationDate.withMonth(month).withDayOfMonth(day);
        Date updatedDate = Date.from(reservationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        return updatedDate;
    }
    
    public static boolean openFrame (JFrame frame){
    	if (!openwindow){
    		openwindow = true;
    		frame.setVisible(true);
    		
    		frame.addWindowListener(new WindowAdapter() {
    			@Override
    			public void windowClosing(WindowEvent e) {
        			//System.out.println("Window is closing...");
    			}

    			@Override
    			public void windowClosed(WindowEvent e) {
    				openwindow = false;
    				
        			//System.out.println("Window fully closed!");
    			}
			});
			return true;
    	} else {
    		JOptionPane.showMessageDialog(null, "Another window is currently open, close the window first.", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		return false;
    	}
    }
    
    public static void addGuest (String name, String email, String contact){
		String id = "G"+GuestCount;
		Guest newGuest = new Guest(name, email, contact);
		guestsDB.put(id, newGuest);
		addLog("Added new Guest: "+name);
	}
	
	public static Guest getGuest (String Id){
		return guestsDB.get(Id);
	}
    
    public static void removeGuest (String Id){
    	guestsDB.remove(Id);
    }
    
    
}