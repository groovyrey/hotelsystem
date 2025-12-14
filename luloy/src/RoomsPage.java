
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
 
public class RoomsPage extends JPanel {
	
    private static String currentType = "SR";
    private static Room currentRoom;
    
    private static JLabel roomIdLabel = new JLabel("RoomId: ---");
    private static JLabel roomStatusLabel = new JLabel("Room Status: ---");
    private static JPanel roomInfoPanel = new JPanel();
   	private static JPanel roomInfoCenter = new JPanel();
   	private static JPanel roomInfoActions = new JPanel();
   	private static String receipt="";
   	private static JButton checkout = new JButton("Checkout");
    private static JButton disableBtn = new JButton("Disable");	
    private static JButton enableBtn = new JButton("Enable");	
   	private static JButton checkin = new JButton("Checkin");
   	
    
    public RoomsPage() {
    	setLayout(new BorderLayout(5,5));
    	
    	JPanel topPanel = new JPanel();
    	JPanel centerPanel = new JPanel();
    	roomInfoCenter.setLayout(new BoxLayout(roomInfoCenter, BoxLayout.Y_AXIS));
    	roomInfoPanel.setBorder(BorderFactory.createTitledBorder("Room Info"));
    	if (currentRoom!=null){
    		roomInfoActions.removeAll();
    		
    		switch(currentRoom.Status){
    			case "V":
    				//System.out.println("Vacant");
    				
		    		disableBtn.setBackground(Color.red);
		    		disableBtn.setForeground(Color.white);
		    		roomInfoActions.add(disableBtn);
    				checkin = new JButton("Check-in");
    				checkin.setBackground(Color.GREEN);
    				//checkin.setForeground(Color.WHITE);
    				roomInfoActions.add(checkin);
    				break;
    			case "O":
    				//System.out.println("Occupied");
    				checkout = new JButton("Check-out");
    				checkout.setBackground(Color.RED);
    				//checkout.setForeground(Color.WHITE);
    				roomInfoActions.add(checkout);
    				break;
    			case "OOO":
    				enableBtn.setBackground(Color.green);
		    		enableBtn.setForeground(Color.black);
		    		roomInfoActions.add(enableBtn);
    				//System.out.println("Out of order");
    				break;
    				
    			default:
    				//System.out.println("Unknown status");
    				break;
    		}
    		disableBtn.addActionListener(dis -> {
    			currentRoom.Status = "OOO";
    			luloy.navigate("Rooms");
    		});
    		enableBtn.addActionListener(dis -> {
    			currentRoom.Status = "V";
    			luloy.navigate("Rooms");
    		});
    		
    		
    		checkin.addActionListener(b -> {
    			JFrame checkinFrame = new JFrame();
    			database.openFrame(checkinFrame);
    			JTextField guestIdLabel = new JTextField(10);
    			checkinFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			checkinFrame.setSize(300,250);
    			checkinFrame.setLocationRelativeTo(null);
    			SpinnerNumberModel num = new SpinnerNumberModel(1.00, 1.00, 30.00, 1);
    			JSpinner nightsSelector = new JSpinner(num);
    			JSpinner.NumberEditor nightseditor = (JSpinner.NumberEditor) nightsSelector.getEditor();
    			nightseditor.getTextField().setEditable(false);
    			JButton confirm = new JButton("Check-In");
    			
    			checkinFrame.setTitle("Check-In: "+currentRoom.Id);
    			
    			checkinFrame.setLayout(new GridBagLayout());
    			
    			GridBagConstraints gbc = new GridBagConstraints();
    			gbc.insets = new Insets(5,5,5,5);
    			
    			gbc.gridx = 0; gbc.gridy = 0;
    			checkinFrame.add(new JLabel("Guest Id: "), gbc);
    			gbc.gridx = 1;
    			checkinFrame.add(guestIdLabel, gbc);
    			gbc.gridx = 0; gbc.gridy = 1;
    			checkinFrame.add(new JLabel("Nights: "), gbc);
    			gbc.gridx = 1; gbc.gridy = 1;
    			checkinFrame.add(nightsSelector, gbc);
    			gbc.gridx = 0; gbc.gridy = 2;
    			checkinFrame.add(new JLabel("Room Type:"), gbc);
    			gbc.gridx = 1; gbc.gridy = 2;
    			checkinFrame.add(new JLabel(currentType), gbc);
    			gbc.gridx = 1; gbc.gridy = 3;
    			checkinFrame.add(confirm, gbc);
    			
    			confirm.addActionListener(e -> {
    				boolean check = validate(guestIdLabel.getText(), Double.parseDouble(nightsSelector.getValue().toString()), currentType);
    				if (check){
    					luloy.navigate("Rooms");
    					checkinFrame.dispose();
    				}
    			});
    		});
    		
    		checkout.addActionListener(cancel -> {
    			receipt = "";
    			Checkin data = database.checkins.get(currentRoom.guest.getId());
    			
    			double totalCosts = (database.RoomtypeRates.get(currentType) * data.nights);
    			
    			String message = "Check-out?\nNights: "+data.nights+"\nRoom Type: "+currentType+"\nTotal: "+totalCosts;
    			
    			int out = JOptionPane.showConfirmDialog(null, message, "Check-Out", JOptionPane.YES_NO_OPTION);
    			
    			if (out==JOptionPane.YES_OPTION){
    				while (true){
    					String cash = JOptionPane.showInputDialog(null, "Enter Amount to pay: \u20B1"+totalCosts, "Payment", JOptionPane.PLAIN_MESSAGE);
    					if (cash == null){
    						System.out.println("Payment Cancelled.");
    						break;
    					} else {
    						double amount = Double.parseDouble(cash);
    						if (amount >= totalCosts){
    							JOptionPane.showMessageDialog(null, "Payment Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
    							receipt+="Receipt: \n";
    							for (int x = 0; x < 20; x++){
    								receipt+="---";
    							}
    							receipt+="\nCosts: "+totalCosts;
    							receipt+="\nCash: "+amount;
    							receipt+="\nChange: "+(amount - totalCosts)+"\n";
    							for (int x = 0; x < 20; x++){
    								receipt+="---";
    							}
    							JOptionPane.showMessageDialog(null, receipt, "Receipt", JOptionPane.PLAIN_MESSAGE);
    							database.checkOut(currentRoom.guest.getId(), data);
    							break;
    						} else {
    							JOptionPane.showMessageDialog(null, "Insufficient amount.", "Error", JOptionPane.ERROR_MESSAGE);
    						}
    					}
    				}
    			} else {
    				System.out.println("Cancelled");
    			}
    		});
    		
    		roomIdLabel.setText("RoomId: "+currentRoom.Id);
    		roomStatusLabel.setText("Room Status: "+currentRoom.Status);
    	}
    	
    	roomInfoActions.setLayout(new GridLayout(0,1));
    	topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    	
    	JLabel dropdownLabel = new JLabel("Room Type: ");
    	String[] roomtypes = {
    		"SR",
    		"DR",
    		"TR",
    		"FR"
    	};
    	JComboBox<String> dropdown = new JComboBox<>(roomtypes);
    	dropdown.setSelectedItem(currentType);
    	dropdown.addActionListener(changed -> {
    		currentType = (String)dropdown.getSelectedItem();
    		luloy.navigate("Rooms");
    	});
    	dropdown.setBackground(Color.decode("#548A70"));
    	dropdown.setForeground(Color.white);
    	
    	topPanel.add(dropdownLabel);
    	topPanel.add(dropdown);
    	roomInfoCenter.add(roomIdLabel);
    	roomInfoCenter.add(roomStatusLabel);
    	
    	roomInfoPanel.setLayout(new BorderLayout(5,5));
    	roomInfoPanel.add(roomInfoCenter, BorderLayout.CENTER);
    	roomInfoPanel.add(roomInfoActions, BorderLayout.SOUTH);
    	
    	database.roomsData.get(currentType).forEach((id, room)->{
    		JPanel itemPanel = new JPanel();
    		String roomNumber = id.split("_")[1];
    		Border line = BorderFactory.createLineBorder(Color.BLACK);
    		
    		JLabel roomId = new JLabel(" "+room.Id+" ");
    		JLabel roomStatus = new JLabel(" "+room.Status+" ");
    		JButton roomName = new JButton("ROOM "+roomNumber+" ("+database.RoomStatus.get(room.Status)+")");
    		
    		itemPanel.setLayout(new BorderLayout(5,5));
    		
    		setStatusColor(room.Status, roomName);
    		
    		roomId.setBorder(line);
    		roomName.setBorder(line);
    		roomStatus.setBorder(line);
    		itemPanel.setBorder(new EmptyBorder(5,5,5,5));
    		
    		itemPanel.add(roomId, BorderLayout.WEST);
    		itemPanel.add(roomName, BorderLayout.CENTER);
    		
    		roomName.addActionListener(a -> {
    			
    			showRoomInfo(room);
    		});
    		
    		centerPanel.add(itemPanel);
    	});
    	
    	
    	add(topPanel, BorderLayout.NORTH);
    	add(centerPanel, BorderLayout.CENTER);
    	add(roomInfoPanel, BorderLayout.EAST);
    }
    
    private static void showRoomInfo (Room targetRoom){
    	if (targetRoom==null){
    		System.out.println("No room object provided.");
    	} else {
    		currentRoom = targetRoom;
    		luloy.navigate("Rooms");
    	}
    }
    
    private static void setStatusColor (String status, JButton target){
    	switch (status){
    		case "V":
    			target.setOpaque(false);
    			target.setBackground(Color.white);
    			break;
    		case "O":
    			target.setBackground(Color.green);
    			break;
    		case "OOO":
    			target.setBackground(Color.orange);
    			break;
    		default:
    			target.setBackground(Color.red);
    			break;
    	}
    }
    
    private static boolean validate (String guestId, double nights, String roomtype) {
    	Guest target = database.getGuest(guestId);
    	
    	if (target==null){
    		JOptionPane.showMessageDialog(null, "Invalid guestId", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	} else {
    		if (target.getStatus() == 2){
    			JOptionPane.showMessageDialog(null,"Guest reserved, Please go to reservation and checkin.", "Error", JOptionPane.ERROR_MESSAGE);
    		} else {
    			System.out.println("Found: "+target.getName());
    			boolean check =	database.newCheckin(target, nights, roomtype, currentRoom);
    		
    			if (check){
    				return true;
    			} else {
    				return false;
    			}	
    		}	
    	}
    	return false;
    }
}
