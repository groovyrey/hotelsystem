import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
	
	interface LoginListener {
		void status (String status);
	}

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    private String user = "admin";
    private String pass = "1234";
    
    public LoginListener listener;

    public LoginFrame(LoginListener argListener) {
    	this.listener = argListener;
        setTitle("Login as Staff");
        setSize(350, 200);
        setLocationRelativeTo(null);   // center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		String path = "C:/Users/Alexander/OneDrive/Documents/JCreator LE/MyProjects/luloy/src/login.png";
		ImageIcon icon = new ImageIcon(path);
		JLabel userIcon = new JLabel();
        userIcon.setPreferredSize(new Dimension(50,50));
        Image img = icon.getImage();
		Image scaled = img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
		userIcon.setIcon(new ImageIcon(scaled));
        setIconImage(icon.getImage());
        // main panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton, gbc);
        
        add(panel);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(user) && password.equals(pass)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.listener.status("SUCCESS");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Invalid username or password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
