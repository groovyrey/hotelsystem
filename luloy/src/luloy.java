/**
 * @(#)luloy.java
 *
 * luloy application
 *
 * @author 
 * @version 1.00 2025/8/20
 */
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 
public class luloy {
    
    
    public static void main(String[] args) {
    	
    	 SwingUtilities.invokeLater(() -> {
            new LoginFrame(stat -> {
                System.out.println("Logged status: " + stat);
                if (stat=="SUCCESS"){
                	runApplication();
                } else {
                	JOptionPane.showMessageDialog(null, "Error logging in", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).setVisible(true);
        });
    }
    
    public static void runApplication (){
    	JFrame main = new JFrame();
    	JPanel leftPanel = new JPanel();
    	JPanel centerPanel = new JPanel();
    	
    	main.setTitle("Main Page");
    	main.setLocationRelativeTo(null);
    	main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	main.setSize(800,600);
    	main.setVisible(true);
    	
    	main.setLayout(new BorderLayout());
    	
    	main.add(new JLabel("Hello, {User}!\nStatus: Stable"), BorderLayout.NORTH);
    	
    	main.add(leftPanel, BorderLayout.WEST);
    	leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    	leftPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
    		
    	CardLayout cards = new CardLayout();
    	main.add(centerPanel, BorderLayout.CENTER);
    	centerPanel.setBackground(Color.YELLOW);
    	centerPanel.setBorder(BorderFactory.createTitledBorder("Page Content"));
    	centerPanel.setLayout(cards); 
    		
    	JButton b1 = new JButton("RESERVATION");
    	JButton b2 = new JButton("ROOMS");
    	JButton b3 = new JButton("GUESTS");
    	JButton b4 = new JButton("CHECK-IN");
    	
    	b1.setAlignmentX(Component.CENTER_ALIGNMENT);
    	b2.setAlignmentX(Component.CENTER_ALIGNMENT);
    	b3.setAlignmentX(Component.CENTER_ALIGNMENT);
    	b4.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	leftPanel.add(b1);
    	leftPanel.add(b2);
    	leftPanel.add(b3);
    	leftPanel.add(b4);
    	
    	GuestPage guestPage = new GuestPage();
    	centerPanel.add(guestPage, "guestpage");
    	cards.show(centerPanel, "guestpage");
    }
}
