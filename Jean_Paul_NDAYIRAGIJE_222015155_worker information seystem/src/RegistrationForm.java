import javax.swing.*;

import java.sql.*;

public class RegistrationForm extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("Welcome to Workers Information Hub");
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel roleLabel = new JLabel("Role:");
    private JTextField roleField = new JTextField();
    private JButton registerButton = new JButton("Register");
    private JButton loginButton = new JButton("Login");

    public RegistrationForm() {
        setLayout(null); // Using null layout for precise positioning

        // Position and size components
        titleLabel.setBounds(150, 20, 300, 30);
        usernameLabel.setBounds(100, 80, 100, 25);
        usernameField.setBounds(210, 80, 150, 25);
        passwordLabel.setBounds(100, 120, 100, 25);
        passwordField.setBounds(210, 120, 150, 25);
        roleLabel.setBounds(100, 160, 100, 25);
        roleField.setBounds(210, 160, 150, 25);
        registerButton.setBounds(150, 210, 100, 30);
        loginButton.setBounds(300, 210, 100, 30);

        // Add components to the frame
        add(titleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(roleLabel);
        add(roleField);
        add(registerButton);
        add(loginButton);

        // Set frame properties
        setTitle("Registration Form");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> goToLogin());
    }

   
    private void registerUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleField.getText();

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis", "222015155", "222015155");

            // Prepared statement for security and efficiency
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                // Clear fields or redirect to login
                
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }

           
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    private void goToLogin() {
       
        Login Login = new Login();
        Login.setVisible(true);
    }

    public static void main(String[] args) {
        RegistrationForm form = new RegistrationForm();
        form.setVisible(true);
    }
}
