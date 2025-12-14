/**
 * @(#)luloy.java
 *
 * luloy application
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Supplier;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.io.File;
import javax.swing.border.*;


public class luloy {

    private static JFrame main = new JFrame();
    private static JPanel leftPanel = new JPanel();
    private static JPanel centerPanel = new JPanel();
    private static JPanel topPanel = new JPanel();
    private static LinkedHashMap<String, Supplier<JPanel>> pageData = new LinkedHashMap<>();
    private static HashMap<String, String> pageIcon = new HashMap<>();
    private static int count = 1;
    private static String defaultPage = "Reservation";
    
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
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current Working Directory: " + workingDir);
        
        topPanel.setBackground(Color.decode("#548A70"));
        leftPanel.setBackground(Color.decode("#548A70"));
        String path = "C:/Users/Administrator/Documents/JCreator Pro/MyProjects/luloy/src/resort.png";
		ImageIcon icon = new ImageIcon(path);
		JLabel userIcon = new JLabel();
        userIcon.setPreferredSize(new Dimension(50,50));
        
        File check = new File(path);
        System.out.println(check.exists());
        
        Image img = icon.getImage();
		Image scaled = img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
		userIcon.setIcon(new ImageIcon(scaled));
		
        main.setTitle("Main Page");
       
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        main.setLayout(new BorderLayout(5, 5));
        main.setIconImage(icon.getImage());
		topPanel.add(userIcon);

		LocalDate datenow = LocalDate.now();
        JLabel GreetingsText = new JLabel("LULOY HOTEL SYSTEM V1");
        GreetingsText.setFont(new Font("Arial", Font.BOLD, 30));
        GreetingsText.setForeground(Color.WHITE);
        GreetingsText.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(GreetingsText);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(leftPanel, BorderLayout.WEST);
        main.add(centerPanel, BorderLayout.CENTER);

        leftPanel.setLayout(new GridLayout(0,1));
        TitledBorder lab = BorderFactory.createTitledBorder("Actions");
        lab.setTitleColor(Color.white);
        leftPanel.setBorder(lab);
		
        centerPanel.setLayout(new BorderLayout());

        pageData.forEach((id, creator) -> {
        	EmptyBorder margin = new EmptyBorder(10,10,10,10);
        	
            JButton button = new JButton(id);
            String imagePath = pageIcon.get(id);
            
            leftPanel.add(button);
			button.setSize(new Dimension(100,100));
			button.setBackground(Color.decode("#345947"));
			button.setForeground(Color.WHITE);
			
			if (imagePath!=null){
				String Iconpath = "C:/Users/Administrator/Documents/JCreator Pro/MyProjects/luloy/src/"+imagePath;
				File iconFile = new File(Iconpath);
				ImageIcon btnIcon = new ImageIcon(Iconpath);
				
				if (iconFile.exists()){
					Image scaledIcon = btnIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
					button.setIcon(new ImageIcon(scaledIcon));
				}
			}
			button.setFocusable(false);
            button.addActionListener(e -> {
                JPanel newPage = creator.get(); 
                navigate(id);
            });
        });
        navigate(defaultPage);
    }
    
    private static void InitiateDatabase () {
    	//Page data initiation
    	pageData.put("Reservation", ReservationPage::new);
    	pageData.put("Guest", GuestPage::new);
        pageData.put("Rooms", RoomsPage::new);
        pageData.put("Logs", LogsPage::new);
        
        //Page icon data
        /*
        Note: The icon(png/jpg) must be placed inside src/ Directory
        */
        pageIcon.put("Guest", "guest.png");
        pageIcon.put("Rooms", "double-bed.png");
        pageIcon.put("Reservation", "reservation.png");
        pageIcon.put("Logs", "file.png");
        //Sample guests
    	database.addGuest("Reymart Centeno", "reymartcenteno03@gmail.com", "09129927548");
    	
        database.addGuest("Eljay Oblino", "eljayobloloy@gmail.com", "---");
        database.addGuest("Jerdick Borbits", "jerdickborbon02@gmail.com", "---");
        database.addGuest("Alexander Pinapit", "marlborored4life@gmail.com", "---");
        
        //Reservation data
        database.addReservation("G0", "SR", 2.00, database.getDate(12,15));
        //Rooms data
        database.RoomTypes.put("SR", "Single Room");
        database.RoomTypes.put("DR", "Double Room");
        database.RoomTypes.put("TR", "Triple Room");
        database.RoomTypes.put("FR", "Family Room");
        //Room rates data
        database.RoomtypeRates.put("SR", 1000.00);
        database.RoomtypeRates.put("DR", 2500.00);
        database.RoomtypeRates.put("TR", 3500.00);
        database.RoomtypeRates.put("FR", 5000.00);
        //Room statuses data
        database.RoomStatus.put("V", "Vacant");
        database.RoomStatus.put("O", "Occupied");
        database.RoomStatus.put("OOO", "Out of Order");
        //Initial example data
        
        
        database.RoomTypes.forEach((id, name)->{
        	LinkedHashMap<String, Room> newTypeData = new LinkedHashMap<>();
        	for (int x = 0; x <= 10; x++){
        		String roomid = id+"_"+x;
        		Room newRoom = new Room(roomid, id);
        		newRoom.Status = "V";
        		newRoom.Type = id;
        		newRoom.Id = roomid;
        		newTypeData.put(roomid, newRoom);
        	}
        	count++;
        	
        	database.roomsData.put(id, newTypeData);
        });
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

