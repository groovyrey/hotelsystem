/**
 * @(#)ReservationPage.java
 *
 *
 * @author 
 * @version 1.00 2025/11/28
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

class ReserveTable extends DefaultTableModel {
	public ReserveTable (Object[][] data, Object[] headers) {
		super(data, headers);
	}
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
}
 
public class ReservationPage extends JPanel {
        
    /**
     * Creates a new instance of <code>ReservationPage</code>.
     */
    public ReservationPage() {
    	setLayout(new BorderLayout(5,5));
    	
    	JPanel topPanel = new JPanel();
    	JPanel centerPanel = new JPanel();
    	JPanel rightPanel = new JPanel();
    	JPanel reservationList = new JPanel();
    	JLabel PageTitle = new JLabel("Reservation Page");
    	
    	topPanel.setLayout(new FlowLayout());
    	topPanel.add(PageTitle);
    	
    	//centerPanel.setBackground(Color.GRAY);
    	centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	
    	add(rightPanel, BorderLayout.EAST);
    	add(topPanel, BorderLayout.NORTH);
    	add(centerPanel, BorderLayout.CENTER);
        JPanel resPanel = new JPanel();
    	resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.Y_AXIS));
    	/*
    	resPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        */
        centerPanel.add(resPanel);
    	
    	database.reservationData.forEach((id, reserve) -> {
    		JPanel itemPanel = new JPanel();
    		itemPanel.setLayout(new FlowLayout());
    		
    	
    		
    		JButton viewButton = new JButton ("...");
    		JLabel idLabel = new JLabel(id);
    		
    		itemPanel.add(viewButton);
    		itemPanel.add(idLabel);
    		
    		resPanel.add(itemPanel);
    		
    		viewButton.addActionListener(e -> {
    			JOptionPane.showMessageDialog(null, "Name: "+reserve.reservedGuest.getName()+"\nRoom Type: "+reserve.RoomType, "Reservation Info", JOptionPane.INFORMATION_MESSAGE);
    		});
    	});
    	
    }
    
    /**
     * @param args the command line arguments
     */
     
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
