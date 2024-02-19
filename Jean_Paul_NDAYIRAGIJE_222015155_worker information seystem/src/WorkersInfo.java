import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WorkersInfo extends JFrame implements ActionListener {

    // UI components declaration
    private JLabel titleLabel, firstNameLabel, lastNameLabel, dobLabel, genderLabel, contactNumberLabel, emailLabel, addressLabel, skillsLabel;
    private JTextField firstNameField, lastNameField, contactNumberField, emailField, addressField, skillsField,dobField,genderField;
    
    private JButton submitButton, assignmentsButton;

    public WorkersInfo() {
        setTitle("Workers Information Hub - Enter Your Details");
        setLayout(null); // Using null layout for manual positioning
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550); // Adjust as needed
        setLocationRelativeTo(null); // Center the form

        // Create and position labels
        

        titleLabel = new JLabel(" Workers Information Hub - Enter Your Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(40, 20, 320, 30);
        add(titleLabel);

        firstNameLabel = new JLabel("firstname:");
        firstNameLabel.setBounds(40, 70, 80, 25);
        add(firstNameLabel);

        lastNameLabel = new JLabel("lastname:");
        lastNameLabel.setBounds(40, 110, 80, 25);
        add(lastNameLabel);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 150, 80, 25);
        add(emailLabel);

        contactNumberLabel = new JLabel("Phone contact:");
        contactNumberLabel.setBounds(40, 190, 80, 25);
        add(contactNumberLabel);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(40, 230, 80, 25);
        add(addressLabel);

        skillsLabel = new JLabel("Skills:");
        skillsLabel.setBounds(40, 270, 80, 25);
        add(skillsLabel);
        
        dobLabel = new JLabel("date of birth:");
        dobLabel.setBounds(40, 310, 80, 25);
        add(dobLabel);
        
        genderLabel = new JLabel("gender:");
        genderLabel.setBounds(40, 350, 80, 25);
        add( genderLabel);




        firstNameField = new JTextField();
        firstNameField.setBounds(130, 70, 200, 25);
        add(firstNameField);

        lastNameField = new JTextField();
        lastNameField.setBounds(130, 110, 200, 25);
        add(lastNameField);

        emailField = new JTextField();
        emailField.setBounds(130, 150, 200, 25);
        add(emailField);

        contactNumberField = new JTextField();
        contactNumberField.setBounds(130, 190, 200, 25);
        add(contactNumberField);

        addressField = new JTextField();
        addressField.setBounds(130, 230, 200, 25);
        add(addressField);

        skillsField = new JTextField();
        skillsField.setBounds(130, 270, 200, 25);
        add(skillsField);
        
        dobField = new JTextField();
        dobField.setBounds(130, 310, 200, 25);
        add( dobField);
        
        genderField = new JTextField();
        genderField.setBounds(130, 350, 200, 25);
        add( genderField);




        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 450, 100, 30);
        submitButton.addActionListener(this);
        add(submitButton);

        assignmentsButton = new JButton("Assignments");
        assignmentsButton.setBounds(270, 450, 120, 30);
        add(assignmentsButton);
        assignmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAssignments().setVisible(true);
            }
        });
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submitDetails();
        }
    }

    private void submitDetails() {
        // Get values from fields and combo boxes
    	 String firstName = firstNameField.getText();
         String lastName = lastNameField.getText();
         String email = emailField.getText();
         String contactnumber = contactNumberField.getText();
         String address = addressField.getText();
         String skills = skillsField.getText();
        String gender = genderField.getText();
        String dob = dobField.getText();
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeanpaul_ndayiragije_wis",  "222015155", "222015155");
            // Prepared statement for inserting data
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO workersinfo (first_name, last_name, date_of_birth, gender, contact_number, email, address, skills) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, dob);
            stmt.setString(4, gender);
            stmt.setString(5, contactnumber);
            stmt.setString(6, email);
            stmt.setString(7, address);
            stmt.setString(8, skills);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Details submitted successfully!");
                
            } else {
                JOptionPane.showMessageDialog(this, "Submission failed. Please try again.");
            }

           
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        WorkersInfo workersInfo = new WorkersInfo();
        workersInfo.setVisible(true);
    }


   
}
