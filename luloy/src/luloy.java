/**
 * @(#)luloy.java
 *
 * luloy application
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Supplier;



public class luloy {

    private static JFrame main = new JFrame();
    private static JPanel leftPanel = new JPanel();
    private static JPanel centerPanel = new JPanel();
    private static JPanel topPanel = new JPanel();
    private static HashMap<String, Supplier<JPanel>> pageData = new HashMap<>();
    
    
    
    //Dev Configurations
    private static boolean bypassLogin = true;
    

    public static void main(String[] args) {
    	if (!bypassLogin){
	        SwingUtilities.invokeLater(() -> {
	            new LoginFrame(stat -> {
	                System.out.println("Logged status: " + stat);
	                if (stat.equals("SUCCESS")) {
	                    runApplication();
	                } else {
	                    JOptionPane.showMessageDialog(null, "Error logging in", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }).setVisible(true);
	        });
    	} else {
    		runApplication();
    	}
    }

    private static void runApplication() {
        InitiateDatabase();
        
        //Default page section
        
        topPanel.setBackground(Color.decode("#548A70"));
        leftPanel.setBackground(Color.decode("#548A70"));
        
        
        main.setTitle("Main Page");
        //main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(800, 500);
        main.setVisible(true);
        main.setLayout(new BorderLayout(5, 5));
		
		/*
		ImageIcon icon = new ImageIcon(main.getClass().getResource("/user.png"));
		JLabel userIcon = new JLabel(icon);
		userIcon.setPreferredSize(new Dimension(50,50));
		*/
        JLabel GreetingsText = new JLabel("Hello, {User}!");
        topPanel.add(GreetingsText);
        //topPanel.add(userIcon);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(leftPanel, BorderLayout.WEST);
        main.add(centerPanel, BorderLayout.CENTER);

        leftPanel.setLayout(new GridLayout(0,1));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        centerPanel.setBorder(BorderFactory.createTitledBorder("Page Content"));
        centerPanel.setLayout(new BorderLayout());

        
        

        
        pageData.forEach((id, creator) -> {
        	
            JButton button = new JButton(id);
            leftPanel.add(button);
			button.setSize(new Dimension(100,100));
			button.setBackground(Color.decode("#345947"));
			button.setForeground(Color.WHITE);
			
            button.addActionListener(e -> {
                JPanel newPage = creator.get(); 
                navigate(id);
            });
        });
        
        navigate("Reservation");
    }
    
    private static void InitiateDatabase () {
    	//Page data initiation
    	pageData.put("Guest", GuestPage::new);
        pageData.put("Rooms", RoomsPage::new);
        pageData.put("Reservation", ReservationPage::new);
        
        //Sample guests
    	database.addGuest("Reymart Centeno", "reymartcenteno03@gmail.com", "09129927548");
        database.addGuest("Eljay Oblino", "eljayobloloy@gmail.com", "---");
        database.addGuest("Jerdick Borbits", "jerdickborbon02@gmail.com", "---");
        database.addGuest("Alexander Pinapit", "marlborored4life@gmail.com", "---");
        
        //Reservation Initiation
        
        
        database.addReservation("G2", "SR", 8, 6);
        database.addReservation("G3", "SR", 8, 6);
        database.addReservation("G1", "SR", 8, 6);
    }
    
    public static void navigate(String id){
    	
    	if (pageData.get(id)==null){
    		System.out.println("Page not found.");
    	} else {
    		JPanel newPage = pageData.get(id).get();
    	
    		centerPanel.removeAll();
        	centerPanel.add(newPage, BorderLayout.CENTER);
        	centerPanel.revalidate();
        	centerPanel.repaint();
    	}
    	
    	
    }
}

