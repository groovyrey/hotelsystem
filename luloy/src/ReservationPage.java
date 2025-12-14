import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
 
public class ReservationPage extends JPanel {
	
	private static String selected = "";
	
    public ReservationPage() {
    	setLayout(new BorderLayout(5,5));
    	JPanel topPanel = new JPanel();
    	JPanel centerPanel = new JPanel();
    	JPanel listPanel = new JPanel();
    	JPanel rightPanel = new JPanel();
    	JPanel reservationList = new JPanel();
    	JLabel PageTitle = new JLabel("Reservation Page");
    	
    	JButton addReservation = new JButton("New Reservation");
    	database.setButtonIcon(addReservation, "add-button.png", 20);
    	addReservation.setBackground(Color.decode("#548A70"));
    	addReservation.setForeground(Color.white);
    	
    	topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	topPanel.add(addReservation);
    	centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	add(rightPanel, BorderLayout.EAST);
    	add(topPanel, BorderLayout.NORTH);
    	add(centerPanel, BorderLayout.CENTER);
        JPanel resPanel = new JPanel();
    	resPanel.setLayout(new GridLayout(0,1));
        centerPanel.setLayout(new GridLayout(0,1));
        centerPanel.add(resPanel);
        Object[][] data = {};
        String[] columnNames = {
        	"Id",
        	"Email",
        	"Room Type",
        	"Date"
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.getTableHeader().setEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        resPanel.add(scroll);
		table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        // Detect double-click
		        if (e.getClickCount() == 2) {
		        	JTable target = (JTable) e.getSource();
		        	String id = (String) table.getValueAt(target.getSelectedRow(), 0);
		        	Reserve reserve = database.reservationData.get(id);
		        	table.clearSelection();
		        	if (reserve!=null){
		        		int choice = JOptionPane.showConfirmDialog(
		                null,
		                "Procceed to check-in this reservation?", 
		                "Confirmation", 
		                JOptionPane.YES_NO_CANCEL_OPTION 
		        		);
		       		 	if (choice == JOptionPane.YES_OPTION) {
		       		 		JFrame checkinFrame = new JFrame();
		       		 		JButton conButton = new JButton("Select");
		       		 		checkinFrame.setLayout(new GridBagLayout());
		       		 		checkinFrame.setTitle("Check-In");
		       		 		checkinFrame.setSize(300,250);
		       		 		checkinFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		       		 		checkinFrame.setLocationRelativeTo(null);
		       		 		GridBagConstraints gbc = new GridBagConstraints();
		       		 		gbc.insets = new Insets(5,5,5,5);
		       		 		
		       		 		String[] roomids = {};
		       		 		JComboBox<String> roomSelector = new JComboBox();
		       		 		
		       		 		database.roomsData.get(reserve.RoomType).forEach((roomid, room)->{
		       		 			if (room.Status == "V"){
		       		 				roomSelector.addItem(roomid);
		       		 			}
		       		 		});
		       		 		
		       		 		gbc.gridx = 0; gbc.gridy = 0;
		       		 		checkinFrame.add(new JLabel("Select Room: "));
		       		 		gbc.gridx = 1;
		       		 		checkinFrame.add(roomSelector, gbc);
		       		 		gbc.gridx = 1; gbc.gridy = 1;
		       		 		checkinFrame.add(conButton, gbc);
		       		 		
		       		 		conButton.addActionListener(tap -> {
		       		 			Guest targetG = reserve.reservedGuest;
		       		 			Room targetRoom = database.roomsData.get(reserve.RoomType).get((String)roomSelector.getSelectedItem());
		       		 			System.out.println("Clicked confirm");
		       		 			if (database.checkins.get(targetG.getId())==null){
		       		 				boolean check =	database.newCheckin(targetG, reserve.nights, reserve.RoomType, targetRoom);
		       		 				if (check){
		       		 					JOptionPane.showMessageDialog(null, "Successfully Checkin!", "Success", JOptionPane.INFORMATION_MESSAGE);
		       		 					checkinFrame.dispose();
		       		 					database.reservationData.remove(id);
		       		 					luloy.navigate("Reservation");
		       		 				}
		       		 			} else {
		       		 				JOptionPane.showMessageDialog(null, "Checkin data already found.", "Error", JOptionPane.ERROR_MESSAGE);
		       		 			}
	       		 		});
	       		 		
	       		 		checkinFrame.setVisible(true);
	        		} else if (choice == JOptionPane.NO_OPTION) {
	           		 	//No action 
	        		} else if (choice == JOptionPane.CANCEL_OPTION) {
	            		int confirm = JOptionPane.showConfirmDialog(null, "Cancel this reservation?", "Cancel?", JOptionPane.YES_NO_OPTION);
	            		
	            		if (confirm == JOptionPane.YES_OPTION){
	            			boolean isRemoved = database.removeReservation(id);
	            			
	            			if (isRemoved){
	            				database.removeGuest(reserve.reservedGuest.getId());
	            				luloy.navigate("Reservation");
	            				JOptionPane.showMessageDialog(null,"Reservation removed!","Success!",JOptionPane.INFORMATION_MESSAGE);
	            			} else {
	            				JOptionPane.showMessageDialog(null,"Reservation failed to remove.","Error",JOptionPane.ERROR_MESSAGE);
	            			}
	            		}
	            		
	        		} else {
	        			//No action
	        		}
		        	} else {
		        		JOptionPane.showMessageDialog(null, "No reserve data with id: "+id, "Error", JOptionPane.ERROR_MESSAGE);
		        	}
		        }
		    }
		});
       
    	database.reservationData.forEach((id, reserve) -> {
    		
    		model.addRow(new String[] {
    			id,
    			reserve.reservedGuest.getEmail(),
    			reserve.RoomType,
    			reserve.date.toLocaleString()
    		});
    	});
    	
    	addReservation.addActionListener(e -> {
    		JFrame resFrame = new JFrame();
    		database.openFrame(resFrame);
    		Date initialDate = new Date();
    		Calendar calendar = Calendar.getInstance();
    		
    		calendar.add(Calendar.YEAR, 100);
        	Date latestDate = calendar.getTime();
        	
        	calendar.setTime(initialDate);
       	 	calendar.add(Calendar.YEAR, -100);
      	  	Date earliestDate = calendar.getTime();
        	
        	SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
        	JSpinner dateSpinner = new JSpinner(dateModel);
        	

        	JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        	dateSpinner.setEditor(dateEditor);
    		
    		resFrame.setTitle("New Reservation");
    		resFrame.setSize(300,250);
    		resFrame.setLocationRelativeTo(null);
    		resFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		
    		JTextField guestIdLabel = new JTextField(10);
    		resFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		resFrame.setLocationRelativeTo(null);
    		SpinnerNumberModel num = new SpinnerNumberModel(1.00, 1.00, 30.00, 1);
    		JSpinner nightsSelector = new JSpinner(num);
    		JSpinner.NumberEditor nightseditor = (JSpinner.NumberEditor) nightsSelector.getEditor();
    		JButton confirm = new JButton("Check-In");
    		
    		dateEditor.getTextField().setEditable(false);
    		nightseditor.getTextField().setEditable(false);
    		
    		resFrame.setLayout(new GridBagLayout());
    		
    		String[] options = {
    			"SR",
    			"DR",
    			"TR",
    			"FR"
    		};
    		JComboBox rt_dropdown = new JComboBox(options);
    			
    		GridBagConstraints gbc = new GridBagConstraints();
    		gbc.insets = new Insets(5,5,5,5);
    				
    		gbc.gridx = 0; gbc.gridy = 0;
    		resFrame.add(new JLabel("Guest Id: "), gbc);
    		gbc.gridx = 1;
    		resFrame.add(guestIdLabel, gbc);
    		gbc.gridx = 0; gbc.gridy = 1;
    		resFrame.add(new JLabel("Nights: "), gbc);
    		gbc.gridx = 1; gbc.gridy = 1;
    		resFrame.add(nightsSelector, gbc);
    		gbc.gridx = 0; gbc.gridy = 2;
    		resFrame.add(new JLabel("Room Type:"), gbc);
    		gbc.gridx = 1; gbc.gridy = 2;
    		resFrame.add(rt_dropdown, gbc);
    		gbc.gridx = 0; gbc.gridy = 3;
    		resFrame.add(new JLabel("Date: "), gbc);
    		gbc.gridx = 1; gbc.gridy = 3;
    		resFrame.add(dateSpinner, gbc);
    		gbc.gridx = 1; gbc.gridy = 4;
    		resFrame.add(confirm, gbc);
    		
    		confirm.addActionListener(click -> {
    			boolean check = validate(guestIdLabel.getText(), Double.parseDouble(nightsSelector.getValue().toString()), (String)rt_dropdown.getSelectedItem(), (Date)dateSpinner.getValue());
    			
    			if (check){
    				JOptionPane.showMessageDialog(null, "Successfully added reservation.", "Success", JOptionPane.INFORMATION_MESSAGE);
    				resFrame.dispose();
    				luloy.navigate("Reservation");
    			}
    		});
    	});
    }
    
    private static boolean validate (String guestid, double nights, String roomtype, Date reservationDate) {
    	int maxDay = LocalDate.now().lengthOfMonth();
    	Guest target = database.getGuest(guestid);
    	
    	if (target!=null){
    		
    		if (nights <= maxDay){
    			database.addReservation(guestid, roomtype, nights, reservationDate);
    			return true;
    		} else {
    			JOptionPane.showMessageDialog(null, "Invalid nights.", "Error", JOptionPane.ERROR_MESSAGE);
    			return false;
    		}
    	} else {
    		JOptionPane.showMessageDialog(null, "Invalid guest id.", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    }
}
