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

class Guest {
	
	private static String  name = "";
	private static String email = "";
	private static String contact = "";
	private static String id = "";
	
	public static void setName (String newname){
		name = newname;
	}
	public static String getName (){
		return name;
	}
	public static void setEmail (String newemail){
		email = newemail;
	}
	public static String getEmail (){
		return email;
	}
	public static void setId (String newId){
		id = newId;
	}	
}

public class database {
	
	
	
    public database() {
    }
    
    
}