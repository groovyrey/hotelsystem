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
import java.util.*;
import java.util.regex.*;

public class GuestPage extends JPanel {
        
    /**
     * Creates a new instance of <code>GuestPage</code>.
     */
     private static JPanel GuestList = new JPanel();
	 private static JPanel ActionsPanel = new JPanel();
	 
	 //Add new guest components
	 private static JTextField nameField;
     private static JTextField emailField;
     private static JTextField contactField;
     private static JButton submitButton;
     
     private static Guest currentGuest;
     private static JLabel nameLabelField;
     private static JLabel emailLabelField;
     private static JLabel contactLabelField;
     
     private static String errorMsg = "Reason: ";
     
     
	 
	 private static ArrayList<ArrayList<String>> initialGuests = new ArrayList<>();
    
    public GuestPage() {
    	setLayout(new BorderLayout());
    	ActionsPanel.setLayout(new FlowLayout());
    	JPanel GuestList = new JPanel();
    	JPanel ActionsPanel = new JPanel();
    	JPanel panel = new JPanel(new GridBagLayout());
    	ActionsPanel.setLayout(new FlowLayout());
    	
    	JButton addGuest = new JButton("Add Guest");
    	JButton removeGuest = new JButton("Remove Guest");
    	
    	ActionsPanel.add(addGuest);
    	ActionsPanel.add(removeGuest);
    	
    	GuestList.setBorder(BorderFactory.createTitledBorder("Guest List:"));
    	GuestList.setLayout(new BoxLayout(GuestList, BoxLayout.Y_AXIS));
    	
    	add(ActionsPanel, BorderLayout.NORTH);
    	add(GuestList, BorderLayout.EAST);
    	add(panel, BorderLayout.CENTER);
    	
    	//Show User Data
    	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

       
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.RED);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameLabelField = new JLabel("Guest Name Here.");
        panel.add(nameLabelField, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setForeground(Color.RED);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailLabelField = new JLabel("Guest Email Here.", SwingConstants.LEFT);
        panel.add(emailLabelField, gbc);

        
        
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel contactLabel = new JLabel("Contact: ");
        contactLabel.setForeground(Color.RED);
        panel.add(contactLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        contactLabelField = new JLabel("Contact number here");
        panel.add(contactLabelField, gbc);
        
        
        gbc.anchor = GridBagConstraints.CENTER;

        add(panel);
    	
    	if (currentGuest!=null){
    		nameLabelField.setText(currentGuest.getName());
    		emailLabelField.setText(currentGuest.getEmail());
    		contactLabelField.setText(currentGuest.getContact());
    	}

    	GuestList.removeAll();
        database.guestsDB.forEach((id, person)->{
    		JButton button = new JButton();
    		button.setText(person.getId());
    		GuestList.add(button);
    		button.addActionListener(e -> showInfo(person));
    	});
    	GuestList.revalidate();
    	GuestList.repaint();
    	
    	removeGuest.addActionListener(rem -> {
    		while(true){
    			String guestId = JOptionPane.showInputDialog(null, "Enter Guest Id", "Remove Guest Prompt", JOptionPane.INFORMATION_MESSAGE);
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
    					database.removeGuest(guestId);
    					JOptionPane.showMessageDialog(null, "Successfully removed guest with Id: "+guestId, "Success", JOptionPane.INFORMATION_MESSAGE);
    					luloy.navigate("Guest");
    					break;
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
    			if (newName.getText()==null||newEmail.getText()==null||newContact.getText()==null){
    				JOptionPane.showMessageDialog(null, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
    			} else if (newName.getText()==""||newEmail.getText()==""||newContact.getText()=="") {
    				JOptionPane.showMessageDialog(null, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
    			} else {
    				boolean checkValidation = validate(newName.getText(), newEmail.getText(), newContact.getText());
    				System.out.println("Is valid? "+checkValidation);
    				
    				if (checkValidation) {
    					database.addGuest(newName.getText(), newEmail.getText(), newContact.getText());
    					frame.dispose();
    					luloy.navigate("Guest");
    					JOptionPane.showMessageDialog(null, "Guest Successfully Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
    				}
    			}
    			
    		});
    		
    		frame.setVisible(true);
    	});
    }
    
    private static boolean validate (String name, String email, String contact){
    	ArrayList<String> errorMessages = new ArrayList<>();
    	errorMsg = "Reason: ";
    	boolean isValid = true;
    	//Email validation section
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        
        //Contact number validation section
        String contactRegex = "^(\\+\\d{1,3}( )?)?(\\(\\d{1,4}\\)|\\d{1,4})[- .]?\\d{3,4}[- .]?\\d{4}$";

        Pattern contactPattern = Pattern.compile(contactRegex);
        Matcher contactMatcher = contactPattern.matcher(contact);
        
        if (!(pattern.matcher(email).matches())){
        	errorMessages.add("Invalid email address format.");
        	isValid = false;
        }
        if (!(contactMatcher.matches())){
        	errorMessages.add("Invalid contact no. format.");
        	isValid = false;
        }
        
        if (isValid){
        	return isValid;
        } else {
        	errorMessages.forEach(error -> {
        		errorMsg+="\n"+error;
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
    /**
     * @param args the command line arguments
     */
}
