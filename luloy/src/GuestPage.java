import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.table.*;

public class GuestPage extends JPanel {
     private static JPanel GuestList = new JPanel();
	 private static JPanel ActionsPanel = new JPanel();
	 
	 private static JTextField nameField;
     private static JTextField emailField;
     private static JTextField contactField;
     private static JButton submitButton;
     
     private static Guest currentGuest;
     private static JTextField nameLabelField;
     private static JTextField emailLabelField;
     private static JTextField contactLabelField;
     private static JTextField roomLabel;
     
     private static String errorMsg = "Reason: ";
	 private static ArrayList<ArrayList<String>> initialGuests = new ArrayList<>();
    
    public GuestPage() {
    	setLayout(new BorderLayout());
    	ActionsPanel.setLayout(new FlowLayout());
    	JPanel GuestList = new JPanel();
    	JPanel ActionsPanel = new JPanel();
    	JPanel panel = new JPanel(new GridBagLayout());
    	JPanel centerPanel = new JPanel();
    	ActionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	boolean hasUser = currentGuest!=null?true:false;
    	
    	JButton addGuest = new JButton("Add Guest");
    	JButton removeGuest = new JButton("Remove Guest");
    	
    	addGuest.setBackground(Color.decode("#548A70"));
    	removeGuest.setBackground(Color.decode("#548A70"));
    	addGuest.setForeground(Color.white);
    	removeGuest.setForeground(Color.white);
    	database.setButtonIcon(addGuest, "add-button.png", 20);
    	database.setButtonIcon(removeGuest, "cross.png", 20);
    	
    	ActionsPanel.add(addGuest);
    	ActionsPanel.add(removeGuest);
    	
    	GuestList.setLayout(new GridLayout(1,0));
    	
    	add(ActionsPanel, BorderLayout.NORTH);
    	add(GuestList, BorderLayout.WEST);
    	add(panel, BorderLayout.EAST);
    	panel.setBorder(BorderFactory.createTitledBorder("Guest Info"));
    	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.decode("#548A70"));
        panel.add(nameLabel, gbc);
		
        gbc.gridx = 1;
        nameLabelField = new JTextField(20);
        nameLabelField.setEditable(false); // Prevents user editing
        nameLabelField.setBorder(null); // Removes the border
        nameLabelField.setOpaque(false);
		nameLabelField.setForeground(UIManager.getColor("Label.foreground"));
        nameLabelField.setFont(UIManager.getFont("Label.font"));
        
        panel.add(nameLabelField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setForeground(Color.decode("#548A70"));
       
        panel.add(emailLabel, gbc);
		
        gbc.gridx = 1;
        emailLabelField = new JTextField(20);
        emailLabelField.setEditable(false);
        emailLabelField.setBorder(null);
        emailLabelField.setOpaque(false);
		emailLabelField.setForeground(UIManager.getColor("Label.foreground"));
        emailLabelField.setFont(UIManager.getFont("Label.font"));
        panel.add(emailLabelField, gbc);
        
        
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel contactLabel = new JLabel("Contact: ");
        contactLabel.setForeground(Color.decode("#548A70"));
        panel.add(contactLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        contactLabelField = new JTextField(20);
        contactLabelField.setEditable(false);
        contactLabelField.setBorder(null);
        contactLabelField.setOpaque(false);
		contactLabelField.setForeground(UIManager.getColor("Label.foreground"));
        contactLabelField.setFont(UIManager.getFont("Label.font"));
        panel.add(contactLabelField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Room: "), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        roomLabel = new JTextField(20);
        roomLabel.setEditable(false);
        roomLabel.setBorder(null);
        roomLabel.setOpaque(false);
		roomLabel.setForeground(UIManager.getColor("Label.foreground"));
        roomLabel.setFont(UIManager.getFont("Label.font"));
        panel.add(roomLabel, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        add(panel);
    	if (currentGuest!=null){
    		nameLabelField.setText(currentGuest.getName());
    		emailLabelField.setText(currentGuest.getEmail());
    		contactLabelField.setText(currentGuest.getContact());
    		roomLabel.setText(currentGuest.getRoom()==null?"None":currentGuest.getRoom().Id);
    	}
		
    	GuestList.removeAll();
    	Object[][] data = {};
        String[] columnNames = {
        	"Email"
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
        GuestList.add(scroll);
        
        table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        // Detect double-click
		        JTable target = (JTable) e.getSource();
		        Guest tarG = database.getGuest((String)target.getValueAt(target.getSelectedRow(), 0));
		        if (e.getClickCount() == 1) {
		        	showInfo(tarG);
		        }
		    }
		});
        
        database.guestsDB.forEach((id, person)->{
    		JButton button = new JButton();
    		int itemrow = 0;
    		button.setBackground(getStatusColor(person.getStatus()));
    		button.setText(person.getEmail());
    		
    		model.addRow(new Object[]{person.getEmail()});
    		itemrow = model.getRowCount() - 1;
    		button.addActionListener(e -> showInfo(person));
    		
    	});
    	GuestList.revalidate();
    	GuestList.repaint();
    	
    	removeGuest.addActionListener(rem -> {
    		while(true){
    			String guestId = JOptionPane.showInputDialog(null, "Enter Guest Id", "Remove Guest Prompt", JOptionPane.PLAIN_MESSAGE);
    			if (guestId==null){
    				System.out.println("Remove guest cancelled");
    				break;
    			}
    			if (guestId.trim().isEmpty()){
    				JOptionPane.showMessageDialog(null, "Enter a  valid Guest Id.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    			} else {
    				Guest findGuest = database.getGuest(guestId);
    				if (findGuest==null){
    					JOptionPane.showMessageDialog(null, "Couldn't find guest with Id: "+guestId, "Error", JOptionPane.ERROR_MESSAGE);
    				} else {
    					if (findGuest.getStatus() == 1 || findGuest.getStatus() == 2){
    						JOptionPane.showMessageDialog(null, "Invalid guest status.", "Error", JOptionPane.ERROR_MESSAGE);
    					} else {
    						database.removeGuest(guestId);
    						JOptionPane.showMessageDialog(null, "Successfully removed guest with Id: "+guestId, "Success", JOptionPane.INFORMATION_MESSAGE);
    						luloy.navigate("Guest");
    						break;
    					}	
    				}
    			}
    		}
    	});
    	
    	addGuest.addActionListener(tap -> {
    		String guestName="";
    		String guestEmail="";
    		String guestContact="";
    		
    		JTextField newName = new JTextField(15);
    		JTextField newEmail = new JTextField(15);
    		JTextField newContact = new JTextField(15);
    		JButton addButton = new JButton("Submit");
    		
    		JFrame frame = new JFrame();
    		database.openFrame(frame);
    		
    		frame.setTitle("Add new Guest");
    		frame.setSize(300,250);
    		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		frame.setLocationRelativeTo(null);
    		
    		frame.setLayout(new GridBagLayout());
    		GridBagConstraints cons = new GridBagConstraints();
    		cons.insets = new Insets(10,10,10,10);
    		
    		cons.gridx = 0; cons.gridy = 0;
    		frame.add(new JLabel("Name: "), cons);
    		
    		cons.gridx = 1; cons.gridy = 0;
    		frame.add(newName, cons);
    		
    		cons.gridx = 0; cons.gridy = 1;
    		frame.add(new JLabel("Email: "), cons);
    		
    		cons.gridx = 1; cons.gridy = 1;
    		frame.add(newEmail, cons);
    		
    		cons.gridx = 0; cons.gridy = 2;
    		frame.add(new JLabel("Contact: "), cons);
    		
    		cons.gridx = 1; cons.gridy = 2;
    		frame.add(newContact, cons);
    		
    		cons.gridx = 1; cons.gridy = 3;
    		frame.add(addButton, cons);
    		
    		addButton.addActionListener(e -> {
    			boolean checkValidation = validate(newName.getText(), newEmail.getText(), newContact.getText());
    			if (checkValidation) {
    				Guest check = database.addGuest(newName.getText(), newEmail.getText(), newContact.getText());
    				frame.dispose();
    				luloy.navigate("Guest");
    				JOptionPane.showMessageDialog(null, "Guest Successfully Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
    			}
    		});
    	});
    }
    
    private static boolean validate (String name, String email, String contact){
    	ArrayList<String> errorMessages = new ArrayList<>();
    	errorMsg = "Reason: ";
    	boolean isValid = true;
    	//Guest validation section
    	Guest target = database.getGuest(email);
    	
    	//Email validation section
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        
        //Contact number validation section
        String contactRegex = "^(\\+\\d{1,3}( )?)?(\\(\\d{1,4}\\)|\\d{1,4})[- .]?\\d{3,4}[- .]?\\d{4}$";

        Pattern contactPattern = Pattern.compile(contactRegex);
        Matcher contactMatcher = contactPattern.matcher(contact);
        
        if (name.isEmpty()){
        	errorMessages.add("Please provide name for the Guest.");
        	isValid = false;
        }
        if (!(pattern.matcher(email).matches())){
        	errorMessages.add("Invalid email address format.");
        	isValid = false;
        }
        
        if (!(contactMatcher.matches())){
        	errorMessages.add("Invalid contact no. format.");
        	isValid = false;
        }
        if (target!=null){
        	errorMessages.add("Guest with email: "+email+" Already exists");
        	isValid = false;
        }
        if (isValid){
        	return isValid;
        } else {
        	errorMessages.forEach(error -> {
        		errorMsg+="\n[ ! ] "+error;
        	});	
        	JOptionPane.showMessageDialog(null,errorMsg,"Input Validator", JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    }
    
    private static void refreshList () {
    	GuestList.removeAll();
        database.guestsDB.forEach((id, person)->{
    		JButton button = new JButton();
    		button.setText(person.getId());
    		GuestList.add(button);
    		button.addActionListener(e -> showInfo(person));
    	});
    	GuestList.revalidate();
    	GuestList.repaint();
    }
    
    private static void showInfo (Guest guest){
    	currentGuest = guest;
    	nameLabelField.setText(guest.getName());
    	emailLabelField.setText(guest.getEmail());
    	luloy.navigate("Guest");
   	}
   	
   	private static Color getStatusColor (int stat){
   		switch (stat){
   			case 0:
   				return Color.WHITE;
   			case 1:
   				return Color.GREEN;
   				
   			case 2:
   				return Color.ORANGE;
   				
   			default:
   				return Color.WHITE;
   		}
   	}
}
