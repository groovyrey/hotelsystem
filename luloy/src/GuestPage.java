/**
 * @(#)GuestPage.java
 *
 *
 * @author 
 * @version 1.00 2025/11/26
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuestPage extends JPanel {
        
    /**
     * Creates a new instance of <code>GuestPage</code>.
     */
    public GuestPage() {
    	setLayout(new BorderLayout());
    	
    	JPanel GuestList = new JPanel();
    	JPanel ActionsPanel = new JPanel();
    	ActionsPanel.setLayout(new FlowLayout());
    	
    	JButton addGuest = new JButton("Add Guest");
    	JButton removeGuest = new JButton("Remove Guest");
    	
    	ActionsPanel.add(addGuest);
    	ActionsPanel.add(removeGuest);
    	
    	GuestList.setBorder(BorderFactory.createTitledBorder("Guest List:"));
    	GuestList.setLayout(new BoxLayout(GuestList, BoxLayout.Y_AXIS));
    	    	
    	JButton g1 = new JButton("Guest 1");
    	JButton g2 = new JButton("Guest 2");
    	JButton g3 = new JButton("Guest 3");
    	
    	GuestList.add(g1);
    	GuestList.add(g2);
    	GuestList.add(g3);
    	
    	add(ActionsPanel, BorderLayout.NORTH);
    	add(GuestList, BorderLayout.WEST);
    	add(new JButton("Button 1"), BorderLayout.CENTER);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
